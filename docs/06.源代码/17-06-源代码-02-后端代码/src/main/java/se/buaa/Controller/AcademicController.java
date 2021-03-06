package se.buaa.Controller;

//200（OK）- 如果现有资源已被更改
//201（created）- 如果新资源被创建
//202（accepted）- 已接受处理请求但尚未完成（异步处理）
//301（Moved Permanently）- 资源的URI被更新
//303（See Other）- 其他（如，负载均衡）
//400（bad request）- 指代坏请求
//404 （not found）- 资源不存在
//406 （not acceptable）- 服务端不支持所需表示
//409 （conflict）- 通用冲突
//412 （Precondition Failed）- 前置条件失败（如执行条件更新时的冲突）
//415 （unsupported media type）- 接受到的表示不受支持
//500 （internal server error）- 通用错误响应
//503 （Service Unavailable）- 服务当前无法处理请求
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;
import se.buaa.Config.JwtUtils;
import se.buaa.Dao.ES_DocumentDao;
import se.buaa.Dao.ES_ExpertDao;
import se.buaa.Dao.ES_KeywordDao;
import se.buaa.Entity.Collection;
import se.buaa.Entity.CollectionKey;
import se.buaa.Entity.Data.Data;
import se.buaa.Entity.Document;
import se.buaa.Entity.ESDocument.ES_Document;
import se.buaa.Entity.ESDocument.ES_Expert;
import se.buaa.Entity.ESDocument.ES_Keyword;
import se.buaa.Entity.Enumeration.CodeEnum;
import se.buaa.Entity.Response.Result;
import se.buaa.FontEntity.*;
import se.buaa.Repository.CollectionRepository;
import se.buaa.Repository.Docu_ExpertRepository;
import se.buaa.Repository.DocumentRepository;
import se.buaa.Service.ES_DocumentService;

//import java.net.http.HttpRequest;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;


//@CrossOrigin
//@CrossOrigin(allowCredentials="false")
@RestController
@RequestMapping("/academic")
public class AcademicController {
    @Autowired
    ES_DocumentDao es_documentDao;
    @Autowired
    ES_KeywordDao es_keywordDao;
    @Autowired
    ES_DocumentService es_documentService;

    @Autowired
    ES_ExpertDao es_expertDao;

    @Autowired
    Docu_ExpertRepository docu_expertRepository;

    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    DocumentRepository documentRepository;

    public static List<String> typeListDefault = new ArrayList<>();

    static int pageSize = 10;

    public ES_ExpertDao getEs_expertDao(){
        return academicController.es_expertDao;
    }

    public Docu_ExpertRepository getDocu_expertRepository(){
        return academicController.docu_expertRepository;
    }

    public static AcademicController academicController;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        academicController = this;
        academicController.docu_expertRepository = this.docu_expertRepository;
        academicController.es_documentDao = this.es_documentDao;
        //初使化时将已静态化的academicController实例化
    }

    @RequestMapping("test1")
    public List<ES_Document> test1(){
        Page<ES_Document> es_documents = es_documentDao.findAll(PageRequest.of(0,10));
        return es_documents.toList();
    }

    @RequestMapping("test/jpafindAll")
    public List<Document> testjpafindAll() {
        Sort.Order order = Sort.Order.desc("time");
        List<Sort.Order> orderList = new ArrayList<>();
//        orderList.add(order1);
        orderList.add(order);
        Sort sort = Sort.by(orderList);
        PageRequest page = PageRequest.of(0, 20,sort);
        Iterable<Document> highCitedList = documentRepository.findAll(page);
        List<Document> documentsList = new ArrayList<>();
        highCitedList.forEach(single ->{documentsList.add(single);});
        return documentsList;
    }

    @RequestMapping("getHighCited")
    public Result<Data> getHighCited() {
        Sort.Order order = Sort.Order.desc("time");
        List<Sort.Order> orderList = new ArrayList<>();
//        orderList.add(order1);
        orderList.add(order);
        Sort sort = Sort.by(orderList);
        PageRequest page = PageRequest.of(0, 20,sort);
        Iterable<ES_Document> highCitedList = es_documentService.findAll(page);
        List<ES_Document> documentsList = new ArrayList<>();
        highCitedList.forEach(single ->{documentsList.add(single);});
        Data data = new Data();
        data.setResult_list(documentsList);
        return new Result("200", CodeEnum.success.toString(), data);

    }

    @RequestMapping("getExpert")
    public Result<Data> getExpert(String expertName,int pageNumber) {
        Date date1 = new Date();
        Sort.Order order = Sort.Order.desc("time");
//        System.out.println(expertName);
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(order);
        Sort sort = Sort.by(orderList);

        if(expertName == null)
            return new Result<>("308", CodeEnum.error.toString());
        List<String> expertsName=new ArrayList<>();

        expertsName.add(expertName);

        List<ES_Document> documentsList = new ArrayList<>();
        List<ES_Document> es_documentList = new ArrayList<>();
        es_documentList = es_documentDao.findByExpertsIn(expertsName);

        Data data = new Data();
        data.total=es_documentList.size();
        if(data.total<10*pageNumber-10)
            return new Result<>("300", CodeEnum.pageOutOfRange.toString(), data);
        if(data.total<10*pageNumber-1)
            data.setResult_list(documentsList.subList(10*pageNumber-10,data.total));
        else
            data.setResult_list(documentsList.subList(10*pageNumber-10,10*pageNumber-1));


        Date date2=new Date();
        data.time= (int) (date2.getTime( )-date1.getTime());
        return new Result<>("200", CodeEnum.success.toString(), data);

    }

    Data getSearchResult(SearchWords searchWords,String sort,List<String> typeList, int pageNum){
        Data data = new Data();
        String searchWords1 = searchWords.getSearchWords();
        String title = searchWords.getTitle();
        String keywords = searchWords.getKeyWords();
        String startYear = searchWords.getStartTime();
        String endYear = searchWords.getEndTime();
        String experts = searchWords.getExperts();
        String origin = searchWords.getOrigin();
        int i=0;
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        System.out.println(searchWords.toString());
        if(searchWords1 != null&& !searchWords1.equals("")){
                QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("title",searchWords1).slop(0);
                boolQueryBuilder.must(queryBuilder);
        }
        if(title != null&& !title.equals("")){
            QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("title",title).slop(0);
            boolQueryBuilder.must(queryBuilder);
            i++;
        }
        if(keywords != null&& !keywords.equals("")){
            QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("keywords",keywords).slop(0);
            boolQueryBuilder.must(queryBuilder);
            i++;
        }
        if(startYear != null && endYear != null){
            QueryBuilder queryBuilder = QueryBuilders.rangeQuery("time").from(startYear).to(endYear)
                    .includeUpper(true).includeLower(true);
            boolQueryBuilder.must(queryBuilder);
            i++;
        }
        if(experts != null&& !experts.equals("")){
//            experts  = experts.replaceAll("[,，\\s;.。]+","*");
            experts="*,"+experts+",*";
            QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("experts",experts).slop(0);
            boolQueryBuilder.must(queryBuilder);

        }
        if(origin != null&& !origin.equals("")){
            QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("origin",origin).slop(0);
            boolQueryBuilder.must(queryBuilder);
            i++;
        }
        if(typeList.size() == 1){
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("dtype", typeList.get(0));
            boolQueryBuilder.must(queryBuilder);
            i++;
        }

        Sort sort1 = getSort(sort);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(pageNum - 1, pageSize,sort1))
                .build();
//        System.out.println(searchQuery.toString());
        Page<ES_Document> es_documents = es_documentDao.search(searchQuery);
        List<ES_Document> es_documentList = es_documents.toList();
        //如果只搜作者，添加返回作者列表
        if(experts!=null&&experts!=""){// TODO: 2020-12-22 org
            List<String> expertList=new ArrayList<>();
            expertList.add(experts);
            PageRequest page = PageRequest.of(0, 6);
            Page<ES_Expert> es_experts = es_expertDao.findByNameIn(page,experts);
            List<ES_Expert> es_expertList = es_experts.toList();
            data.setExpert_list(es_expertList);
        }
        data.setTotal((int) es_documents.getTotalElements());
        data.setResult_list(es_documentList);
        return data;
    }

    Data getSearchResult(Map<String,String> searchMap,List<String> typeList, int pageNum){
        Data data = new Data();
        String keywords = searchMap.get("keywords");
        String startYear = searchMap.get("startYear");
        String endYear = searchMap.get("endYear");
        String experts = searchMap.get("experts");
        String origin = searchMap.get("origin");
        String sort = searchMap.get("sort");

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        if(keywords != null){
            QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("*" + keywords + "*",//keywords,//"*" + keywords + "*",
                    "title");
            boolQueryBuilder.must(queryBuilder);
        }
        if(startYear != null && endYear != null){
            QueryBuilder queryBuilder = QueryBuilders.rangeQuery("time").from(startYear).to(endYear)
                    .includeUpper(true).includeLower(true);
            boolQueryBuilder.must(queryBuilder);
        }
        if(experts != null){
            QueryBuilder queryBuilder;
            experts  = experts.replaceAll("[,，\\s;.。]+","*");
//            System.out.println(experts);
            queryBuilder = QueryBuilders.wildcardQuery( "experts","*" + experts + "*");
            boolQueryBuilder.must(queryBuilder);
        }
        if(origin != null){
            QueryBuilder queryBuilder = QueryBuilders.wildcardQuery( "origin","*" + origin + "*");
            boolQueryBuilder.must(queryBuilder);
        }
        if(typeList.size() == 1){
            QueryBuilder queryBuilder = QueryBuilders.matchQuery("dtype", typeList.get(0));
            boolQueryBuilder.must(queryBuilder);
        }

        Sort sort1 = getSort(sort);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(pageNum - 1, pageSize,sort1))
                .build();

        Page<ES_Document> es_documents = es_documentDao.search(searchQuery);
        List<ES_Document> es_documentList = es_documents.toList();

        data.setTotal((int) es_documents.getTotalElements());
        data.setResult_list(es_documentList);
        return data;
    }

    @RequestMapping("getKeyword")
    public Result<Data> getKeyword(String keyword,int pageNumber,String type) {
        Date date1=new Date();
        Sort.Order order = Sort.Order.desc("cited");
        List<Sort.Order> orderList = new ArrayList<>();
//        orderList.add(order1);
        orderList.add(order);
        Sort sort = Sort.by(orderList);

        if(keyword == null)
            return new Result("308", CodeEnum.error.toString());
        List<String> typeList=new ArrayList<>();
        if(type == null){
            typeList = typeListDefault;
        }
        else {
            typeList.add(type);
        }
        PageRequest page= PageRequest.of(pageNumber-1,10);
        List<ES_Document> documentsList = new ArrayList<>();
        Iterable<ES_Document> highCitedList = es_documentDao.findByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
                page,keyword,"","","1970","2020",typeList,
                keyword,"","","1970","2020",typeList,
                keyword,"","","1970","2020",typeList
        );
        highCitedList.forEach(single ->{documentsList.add(single);});

        Data data = new Data();
        data.total=es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
                keyword,"","","1970","2020",typeList,
                keyword,"","","1970","2020",typeList,
                keyword,"","","1970","2020",typeList
        );
        data.result_list=documentsList;
        Date date2=new Date();
        data.time= (int) (date2.getTime( )-date1.getTime());
        ES_Keyword es_keyword = es_keywordDao.findByKeyword(keyword);
        if(es_keyword != null){
            es_keyword.setView(es_keyword.getView() + 1);
            es_keywordDao.save(es_keyword);
        }
        return new Result("200", CodeEnum.success.toString(), data);

    }
    private List<ES_Document> clean(List<ES_Document> es_documents,String experts) {
        boolean k=false;
        int l=es_documents.size();
        for (int i=0;i<l;i++){
            k=false;
            int l2=es_documents.get(i).getAuthors().size();
            for(int j=0;j<l2;j++){
                if(es_documents.get(i).getAuthors().get(j).getName().compareTo(experts)==0) {
                    k = true;
                    break;
                }
            }
            if(!k){
                es_documents.remove(i);
                i--;
                l--;
            }

        }
        return es_documents;
    }

    //{a=1, b=2, c=3} 格式转换成map
    private static Map<String, String> mapStringToMap(String str) {
        str = str.replaceAll("\"","");
        str = str.substring(1, str.length() - 1);
        String[] strs = str.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (String string : strs) {
            String key = string.split(":")[0].trim();
            String value = string.split(":").length == 1 ? null:string.split(":")[1];
            map.put(key, value);
        }
        return map;
    }

    public CodeEnum checkSearchInput(Post post){
        if(post.getPage() == null)
            post.setPage("1");

        if(post.getUserID() == null )
            post.setUserID("-1");

        if(post.getSort() == null)
            return CodeEnum.noSort;

        return  CodeEnum.success;
    }

    public CodeEnum handleSearchInput(Post post){
        try {
            Integer.parseInt(post.getPage());
        }
        catch (Exception e) {
            return CodeEnum.pageNotInteger;
        }

        SearchWords searchWords = post.getSearch_words();

        if(searchWords.getExperts() == null)
            searchWords.setExperts("");

        if(searchWords.getEndTime() == null || searchWords.getEndTime().equals("") || searchWords.getEndTime().equals("0"))
            searchWords.setEndTime("2020");

        FilterWords filterWords = post.getFilter_words();

        String year = filterWords.getYear();

        if(year != null ){
            if(!"".equals(year)) {
//                try {
//                    Integer.parseInt(year);
//                }
//                catch (Exception e) {
//                    return CodeEnum.yearNotInteger;
//                }
                searchWords.setStartTime(filterWords.getYear());
            }
        }
        else if(searchWords.getStartTime() == null || searchWords.getStartTime().equals(""))
            searchWords.setStartTime("0");

//        try {
//            Integer.parseInt(searchWords.getStartTime());
//            Integer.parseInt(searchWords.getEndTime());
//        }
//        catch (Exception e) {
//            return CodeEnum.yearNotInteger;
//        }

        return  CodeEnum.success;
    }

    public CodeEnum checkAndHandleSearchInput(Post post){
        CodeEnum result = checkSearchInput(post);

        if(result != CodeEnum.success)
            return result;

        return handleSearchInput(post);
    }

    public Sort getSort(String sort){
        Sort.Order order;
        switch (sort) {
            case "time":
                order = Sort.Order.desc("time"); break;
            case "cited":
                order = Sort.Order.desc("cited_quantity"); break;
            case "citedNum":
                order = Sort.Order.desc("citedNum"); break;
            case "view":
                order = Sort.Order.desc("view"); break;
            default:
                order = Sort.Order.desc("views");
        }
        List<Sort.Order> orderList = new ArrayList<>();
        orderList.add(order);
        Sort sort1 = Sort.by(orderList);
        return sort1;
    }

    public List<String> getTypeList(String type){
        List<String> typeList = new ArrayList<>();
        if(type == null || type.equals("")) typeList = typeListDefault;
        else typeList.add(type);
        return typeList;

    }

    @PostMapping("getSearchResult")
    public Result<Data> getSearchResult(@RequestBody Post post) {
        //判断输入是否合法，并处理相关数据
        CodeEnum result = checkAndHandleSearchInput(post);
        if(result != CodeEnum.success) return new Result<>(result.getCode(),result.toString(),new Data());
        Date date1=new Date();
        //变量声明
        int pageNum;
        String page = post.getPage();
        String sort = post.getSort();
        String userID = post.getUserID();
        SearchWords search_word = post.getSearch_words();
        FilterWords filterWords=post.getFilter_words();
        List<String> typeList = getTypeList(filterWords.getType());

        pageNum = Integer.parseInt(page);
        if(pageNum < 1) return new Result<>(CodeEnum.pageLessThanOne.getCode(),CodeEnum.pageLessThanOne.toString(),new Data());

        Data data = getSearchResult(search_word,sort,typeList,pageNum);
        int total = data.total;
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        if(totalPage == 0&&data.getExpert_list().size()==0)
            return new Result<Data>(CodeEnum.noResult.getCode(),CodeEnum.noResult.toString(),new Data());
        if( totalPage < pageNum &&data.getExpert_list().size()==0)
            return new Result<Data>(CodeEnum.pageOutOfRange.getCode(),CodeEnum.pageOutOfRange.toString(),new Data());
        if(userID.compareTo("-1")!=0){
            for (ES_Document es_document : data.getResult_list()) {
                String doc_id = es_document.getId();
                CollectionKey ck = new CollectionKey(Integer.parseInt(userID), doc_id);
                Optional<Collection> res = collectionRepository.findById(ck);
                es_document.setIs_favor(res.isPresent());
            }
        }
        data.filter_list.add(getTimeFilter(search_word,typeList));
        data.filter_list.add(getTypeFilter(search_word,typeList));
        Date date2=new Date();
        data.time= (int) (date2.getTime()-date1.getTime());

        return new Result<>(CodeEnum.success.getCode(),CodeEnum.success.toString(),data);
    }
    private Filter getTimeFilter(SearchWords searchWords,List<String> typeList){
        Filter filter=new Filter();
        filter.filter_name = "year";
        filter.title = "时间";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String year = searchWords.getEndTime();
        if(year == null || year.length() < 4)
            year = simpleDateFormat.format(new Date()).substring(0,4);
        year = year.substring(0,4);
        int l=searchWords.getStartTime().length();
        if(l>4)
            l=4;
        String startYear = searchWords.getStartTime().substring(0,l);
        int start = Integer.parseInt(startYear);

        int[] years = new int[5];
        years[0] = Integer.parseInt(year);
        years[1] = years[0] - 1;
        years[2] = years[0] - 2;
        years[3]  = years[0] - 5;
        years[4]  = years[0] - 10;
        SearchWords searchWords1  = new SearchWords();
        BeanUtils.copyProperties(searchWords, searchWords1);
        for(int i = 0; i < 5 ;i++){
            searchWords1.setStartTime(String.valueOf(years[i]));
            Filter_Item filter_item = new Filter_Item();
            filter_item.content = String.valueOf(years[i]);
            filter_item.count = getSearchResult(searchWords1,"cited",typeList,1).getTotal();
            if(start <= years[i])
                filter.filter_itemList.add(filter_item);
        }
        return filter;
//        int count1 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year1.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year1.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year1.toString(),searchWords.getEndTime(),typeList
//        );
//        int count2 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year2.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year2.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year2.toString(),searchWords.getEndTime(),typeList
//        );
//        int count3 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year3.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year3.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year3.toString(),searchWords.getEndTime(),typeList
//        );
//        int count4 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year4.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year4.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year4.toString(),searchWords.getEndTime(),typeList
//        );
//        int count5 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year5.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year5.toString(),searchWords.getEndTime(),typeList,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),year5.toString(),searchWords.getEndTime(),typeList
//        );
//        Filter_Item filter_item1=new Filter_Item();
//        Filter_Item filter_item2=new Filter_Item();
//        Filter_Item filter_item3=new Filter_Item();
//        Filter_Item filter_item4=new Filter_Item();
//        Filter_Item filter_item5=new Filter_Item();
//        filter_item1.content=year1.toString();
//        filter_item1.count=count1;
//        filter_item2.content=year2.toString();
//        filter_item2.count=count2;
//        filter_item3.content=year3.toString();
//        filter_item3.count=count3;
//        filter_item4.content=year4.toString();
//        filter_item4.count=count4;
//        filter_item5.content=year5.toString();
//        filter_item5.count=count5;
//        if(start<=year1)
//            filter.filter_itemList.add(filter_item1);
//        if(start<=year2)
//            filter.filter_itemList.add(filter_item2);
//        if(start<=year3)
//            filter.filter_itemList.add(filter_item3);
//        if(start<=year4)
//            filter.filter_itemList.add(filter_item4);
//        if(start<=year5)
//            filter.filter_itemList.add(filter_item5);
    }
    private Filter getTypeFilter(SearchWords searchWords,List<String> typeList){// TODO: 2020-12-18 filter还没有传并接受
        String[] type = {
                "期刊",
                "会议",
                "专利",
                "图书",
                "学位"
        };

        Filter filter=new Filter();
        filter.filter_name="type";
        filter.title="种类";

        if(typeList.size() == 5) {
            for (int i = 0; i < 5; i++) {
                Filter_Item filter_item = new Filter_Item();
                filter_item.count = getSearchResult(searchWords,"cited",getTypeList(typeList.get(i)),1).getTotal();
                filter_item.content = typeList.get(i);
                filter.filter_itemList.add(filter_item);
            }
        }
        else{
            Filter_Item filter_item = new Filter_Item();
            filter_item.count = getSearchResult(searchWords,"cited",getTypeList(typeList.get(0)),1).getTotal();
            filter_item.content = typeList.get(0);
            filter.filter_itemList.add(filter_item);
        }
        return filter;

//        List<String> typeList1=new ArrayList<>();
//        typeList1.add("期刊");
//        List<String> typeList2=new ArrayList<>();
//        typeList2.add("会议");
//        List<String> typeList3=new ArrayList<>();
//        typeList3.add("专利");
//        List<String> typeList4=new ArrayList<>();
//        typeList4.add("图书");
//        List<String> typeList5=new ArrayList<>();
//        typeList5.add("学位");
//
//        int count1 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList1,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList1,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList1
//        );
//        int count2 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList2,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList2,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList2
//        );
//        int count3 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList3,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList3,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList3
//        );
//        int count4 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList4,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList4,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList4
//        );
//        int count5 = es_documentDao.countByTitleInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrSummaryInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeInOrKeywordsInAndExpertsLikeAndOriginLikeAndTimeBetweenAndDtypeIn(
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList5,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList5,
//                searchWords.getKw(),searchWords.getExperts(),searchWords.getOrigin(),searchWords.getStartTime(),searchWords.getEndTime(),typeList5
//        );
//        Filter_Item filter_item1=new Filter_Item();
//        Filter_Item filter_item2=new Filter_Item();
//        Filter_Item filter_item3=new Filter_Item();
//        Filter_Item filter_item4=new Filter_Item();
//        Filter_Item filter_item5=new Filter_Item();
//        filter_item1.content="期刊";
//        filter_item1.count=count1;
//        filter_item2.content="会议";
//        filter_item2.count=count2;
//        filter_item3.content="专利";
//        filter_item3.count=count3;
//        filter_item4.content="图书";
//        filter_item4.count=count4;
//        filter_item5.content="学位";
//        filter_item5.count=count5;
//        if(typeList.size()==5){
//            filter.filter_itemList.add(filter_item1);
//            filter.filter_itemList.add(filter_item2);
//            filter.filter_itemList.add(filter_item3);
//            filter.filter_itemList.add(filter_item4);
//            filter.filter_itemList.add(filter_item5);
//        }
//        else if(typeList.get(0).compareTo("期刊")==0){
//            filter.filter_itemList.add(filter_item1);
//        }
//        else if(typeList.get(0).compareTo("会议")==0){
//            filter.filter_itemList.add(filter_item2);
//        }
//        else if(typeList.get(0).compareTo("专利")==0){
//            filter.filter_itemList.add(filter_item3);
//        }
//        else if(typeList.get(0).compareTo("图书")==0){
//            filter.filter_itemList.add(filter_item4);
//        }
//        else if(typeList.get(0).compareTo("学位")==0){
//            filter.filter_itemList.add(filter_item5);
//        }
    }
    @RequestMapping("getById")
    public Result<ES_Document> getById(String document_id,int user_id){
        System.out.println(user_id);
        if(document_id == null)
            return new Result<>(CodeEnum.docIdNotExist.getCode(), CodeEnum.docIdNotExist.toString(),null);
        ES_Document es_document = es_documentDao.findById(document_id).get();
        if(es_document == null)
            return new Result<>(CodeEnum.documentNotExist.getCode(), CodeEnum.documentNotExist.toString(),null);
        else{
            List<String> _keywords = es_document.getKeywordList();
            System.out.println(_keywords);
            for (String keyword : _keywords) {
                ES_Keyword es_keyword = es_keywordDao.findByKeyword(keyword);
                if(es_keyword != null){
                    es_keyword.setView(es_keyword.getView() + 1);
                    es_keywordDao.save(es_keyword);
                }
            }
            CollectionKey collectionKey;
            if(user_id!=-1){
                collectionKey = new CollectionKey(user_id, document_id);
                Optional<Collection> res = collectionRepository.findById(collectionKey);
                es_document.setIs_favor(res.isPresent());
            }
            es_document.setViews(es_document.getViews()+1);
            es_documentDao.save(es_document);
            return new Result<>(CodeEnum.success.getCode(), CodeEnum.success.toString(),es_document);
        }

    }

    @GetMapping("favorSc")
    public Result<Boolean> favorSc(@RequestParam("document_id") String doc_id,@RequestParam("user_id") int user_id,@RequestParam("token") String token){
        if (JwtUtils.verifyToken(token)!=0){
            return Result.Error("201","token非法，请重新登录");
        }
        CollectionKey ck =new CollectionKey(user_id,doc_id);
        Optional<Collection> res = collectionRepository.findById(ck);
        if(res.isPresent())
        {
            collectionRepository.deleteById(ck);
            return Result.Success(false);
        }
        else
        {
            Collection collection = new Collection(ck);
            collectionRepository.save(collection);
            return Result.Success(true);
        }

    }
    @GetMapping("getHotKeywords")
    public Result<ES_Keyword> getHotKeywords(){
//治疗 7472 Agents 989 Proteins 845
        Sort sort1 = getSort("view");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(0, 20,sort1))
                .build();
        Page<ES_Keyword> es_keywords = es_keywordDao.search(searchQuery);
//        ES_Keyword es_keyword=new ES_Keyword();
//        es_keyword.keyword="治疗";
//        es_keyword.citedNum=7472;
//        ES_Keyword es_keyword2=new ES_Keyword();
//        es_keyword2.keyword="经济";
//        es_keyword2.citedNum=7223;
//        ES_Keyword es_keyword3=new ES_Keyword();
//        es_keyword3.keyword="化学";
//        es_keyword3.citedNum=5187;
//        ES_Keyword es_keyword4=new ES_Keyword();
//        es_keyword4.keyword="治疗";
//        es_keyword4.citedNum=7472;
//        ES_Keyword es_keyword5=new ES_Keyword();
//        es_keyword5.keyword="工程";
//        es_keyword5.citedNum=4263;
//        ES_Keyword es_keyword6=new ES_Keyword();
//        es_keyword6.keyword="材料";
//        es_keyword6.citedNum=2845;
//        ES_Keyword es_keyword7=new ES_Keyword();
//        es_keyword7.keyword="计算机";
//        es_keyword7.citedNum=2333;
//        ES_Keyword es_keyword8=new ES_Keyword();
//        es_keyword8.keyword="糖尿病";
//        es_keyword8.citedNum=2131;
//        ES_Keyword es_keyword9=new ES_Keyword();
//        es_keyword9.keyword="电子";
//        es_keyword9.citedNum=1817;
//        ES_Keyword es_keyword10=new ES_Keyword();
//        es_keyword10.keyword="数学";
//        es_keyword10.citedNum=1523;
//        List<ES_Keyword> row = new ArrayList<>();
//        row.add(es_keyword);
//        row.add(es_keyword2);
//        row.add(es_keyword3);
//        row.add(es_keyword4);
//        row.add(es_keyword5);
//        row.add(es_keyword6);
//        row.add(es_keyword7);
//        row.add(es_keyword8);
//        row.add(es_keyword9);
//        row.add(es_keyword10);
//        Collections.sort(row);
//        return new Result("200", CodeEnum.success.toString(),row);
        System.out.println(es_keywords);
        return new Result("200", CodeEnum.success.toString(),es_keywords.getContent());
    }
    public static List<Integer> getMax10Value(Map<String, Integer> map) {
        if (map == null)
            return null;
        int length =map.size();
        java.util.Collection<Integer> c = map.values();
        Object[] obj = c.toArray();
        Arrays.sort(obj);
        List<Integer> list=new ArrayList<>();
        for(int i=1;i<=10;i++){
            list.add((Integer) obj[length-i]);
        }
        return list;
    }
}
