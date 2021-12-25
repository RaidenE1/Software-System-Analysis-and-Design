package se.buaa.Controller;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import se.buaa.Dao.ES_DocumentDao;
import se.buaa.Dao.ES_ExpertDao;
import se.buaa.Entity.Data.Achievement;
import se.buaa.Entity.Data.Data;
import se.buaa.Entity.Data.ScholarInfo;
import se.buaa.Entity.Document;
import se.buaa.Entity.ESDocument.ES_Document;
import se.buaa.Entity.ESDocument.ES_Expert;
import se.buaa.Entity.Enumeration.CodeEnum;
import se.buaa.Entity.Expert;
import se.buaa.Entity.Relation.Document_Expert;
import se.buaa.Entity.Response.Result;
import se.buaa.Entity.User_Expert;
import se.buaa.Exception.ScholarException;
import se.buaa.Repository.Docu_ExpertRepository;
import se.buaa.Repository.DocumentRepository;
import se.buaa.Repository.ExpertRepository;
import se.buaa.Repository.User_ExpertRepository;

import java.util.*;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
public class ScholarController {

    @Autowired
    ExpertRepository expertRepository;
    @Autowired
    Docu_ExpertRepository docu_expertRepository;
    @Autowired
    DocumentRepository documentRepository;
    @Autowired
    User_ExpertRepository user_expertRepository;
    @Autowired
    ES_DocumentDao es_documentDao;
    @Autowired
    ES_ExpertDao es_expertDao;

    /** 获取科研人员相关信息 */
    @GetMapping("/scholar/getInfo")
    public Result getScholarInfo(@RequestParam(value = "scholar_id") String scholar_id
                                    , @RequestParam(value = "user_id") String user_id) {
        Expert expert = expertRepository.findByExpertID(scholar_id);
        System.out.println(scholar_id);
        System.out.println(expert);
        // 错误处理，类型码在 ErrorConfig.xml 文件中，类型码要和创建的 Exception 类关联
        // Exception 类的创建方法详见 ScholarException
        if (expert == null) return Result.Error(new ScholarException("00"));

        ScholarInfo scholarInfo = new ScholarInfo();

        scholarInfo.name = expert.getName();
        scholarInfo.volume = expert.getViews();
        scholarInfo.scholar_id = expert.getExpertID();
        scholarInfo.affiliate = expert.getOrg();
        scholarInfo.isVerified = false;
        if(expert.getIsVerified() != null && expert.getIsVerified() == 1){
            scholarInfo.isVerified = true;
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("expertid", ExampleMatcher.GenericPropertyMatcher::exact)
                .withIgnorePaths("id").withIgnorePaths("documentid");
        Document_Expert tmpRelation = new Document_Expert();
        tmpRelation.setExpertID(scholar_id);
        Example<Document_Expert> example = Example.of(tmpRelation, matcher);
        List<Document_Expert> exp_docs = docu_expertRepository.findAll(example);

        int size = Math.min(exp_docs.size(), 4);

        for (int i = 0; i < size; i++) {
            ES_Document doc = es_documentDao.findByDocumentid(exp_docs.get(i).getDocumentID());
            scholarInfo.achList.add(new Achievement(doc.getTitle(), doc.getCited_quantity()));
        }

        int userId = Integer.parseInt(user_id);
        List<User_Expert> list = user_expertRepository.findByUserIdAndExpertId(userId, scholar_id);

        scholarInfo.isFocus = list.size() != 0;

        return Result.Success(scholarInfo);
    }

    /** 关注、取消关注 */
    @GetMapping("/scholar/focusScholar")
    public Result focusScholar(@RequestParam(value = "scholar_id") String scholar_id
                            , @RequestParam(value = "user_id") String user_id) {
        int userId = Integer.parseInt(user_id);
        List<User_Expert> list = user_expertRepository.findByUserIdAndExpertId(userId, scholar_id);

        if (list.size() == 0) {
            User_Expert ue = new User_Expert(userId, scholar_id);
            user_expertRepository.save(ue);
            return Result.Success(true);
        }
        else {
            user_expertRepository.delete(list.get(0));
            return Result.Success(false);
        }
    }

    @GetMapping("/scholar/getCoAuthors")
    public Result<Data> getCoAuthors(@RequestParam(value = "scholar_id") String scholar_id){
        return getDataResult(scholar_id);
    }

    @GetMapping("/scholar/getCoAffiliate")
    public Result<Data> getCoAffiliate(String scholar_id){
        return getDataResult(scholar_id);
    }

    private Result<Data> getDataResult(String scholar_id) {
        ES_Expert expert = es_expertDao.findByExpertid(scholar_id);
        if(expert == null)
            return new Result<>(CodeEnum.noExpert.getCode(), CodeEnum.noExpert.toString(),new Data());
        String name = expert.getName();
        Sort.Order order = Sort.Order.desc("cited_quantity");
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(order);
        Sort sort = Sort.by(orderList);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("experts",name );
        boolQueryBuilder.must(queryBuilder);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(0, 10,sort))
                .build();
        Iterable<ES_Document> es_documents = es_documentDao.search(searchQuery);
        Data data = new Data();
        int num = 0;
        List<ES_Expert> es_expertList = new ArrayList<>();
        for(ES_Document es_document : es_documents){
            for(ES_Expert es_expert : es_document.getAuthors()){
                if(es_expert.getName() != null && !Pattern.matches("\\s*",es_expert.getName())
                        && !es_expert.getName().equals(name)){
                    List<ES_Expert> es_expertList1 = es_expertDao.findByName(es_expert.getName());
                    if(es_expertList1 != null && es_expertList1.size() != 0) {
                        ES_Expert es_expert1 = es_expertList1.get(0);
                        if(es_expert1.getCooperationNum() == 0) {
                            es_expert1.setCooperationNum((int) (Math.random() * 10));
                            es_expertDao.save(es_expert1);
                        }
                        boolean flag = false;
                        for(ES_Expert _expert : es_expertList){
                            if(_expert.getId().equals(es_expert1.getId())){
                                flag = true;
                                break;
                            }
                        }
                        if(flag == false){
                            es_expertList.add(es_expert1);
                            num++;
                        }
                        if (num == 10) {
                            data.setExpert_list(es_expertList);
                            data.setTotal(10);
                            return new Result<>(CodeEnum.success.getCode(), CodeEnum.success.toString(), data);
                        }
                    }
                }

            }
        }
        data.setExpert_list(es_expertList);
        data.setTotal(num);
        return new Result<>(CodeEnum.success.getCode(),CodeEnum.success.toString(),data);
    }

}
