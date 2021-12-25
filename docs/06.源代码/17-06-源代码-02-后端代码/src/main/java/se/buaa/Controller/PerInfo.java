package se.buaa.Controller;

import io.jsonwebtoken.Jwt;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;
import se.buaa.Config.JwtUtils;
import se.buaa.Dao.ES_DocumentDao;
import se.buaa.Entity.Data.Achievement;
import se.buaa.Entity.Data.FollowedExpertInfo;
import se.buaa.Entity.Data.ScholarInfo;
import se.buaa.Entity.ESDocument.ES_Document;
import se.buaa.Entity.Enumeration.CodeEnum;
import se.buaa.Entity.Expert;
import se.buaa.Entity.Relation.Document_Expert;
import se.buaa.Entity.Response.Result;
import se.buaa.Entity.User;
import se.buaa.Entity.User_Expert;
import se.buaa.Repository.Docu_ExpertRepository;
import se.buaa.Repository.ExpertRepository;
import se.buaa.Repository.UserRepository;
import se.buaa.Repository.User_ExpertRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//this class maps four request:/user/getPerInfo, /user/changeInfo, /user/changeImg and /user/changePasswd,
//which relates to personInfo

@RestController
public class PerInfo {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExpertRepository expertRepository;
    @Autowired
    User_ExpertRepository user_expertRepository;
    @Autowired
    Docu_ExpertRepository docu_expertRepository;
    @Autowired
    ES_DocumentDao es_documentDao;

    @RequestMapping("/user/getPerInfo")
    @ResponseBody
    public Result getPerInfo(@RequestParam int userID){
        Data data = new Data();
        User user = userRepository.findByUserID(userID);
        if (user==null){
            return Result.Error("201","用户不存在");
        }
        else{
            data.email = user.email;
            data.phoneNum = user.phoneNum;
            data.realName = user.realName;
            data.url = user.url;
            data.userName = user.userName;
            return Result.Success(data);
        }
    }


    @RequestMapping("/user/changeInfo")
    @ResponseBody
    public Result changeInfo(@RequestParam int userID,String realName,String email,String phoneNum){
        User user = userRepository.findByUserID(userID);
        if (user==null){
            return Result.Error("201","用户不存在");
        }
        else {
            user.email = email;
            user.phoneNum = phoneNum;
            user.realName = realName;
            userRepository.save(user);
            return Result.Success();
        }
    }

    @RequestMapping("/user/changeImg")
    @ResponseBody
    public Result changeImg(@RequestParam int userID, String url){
        User user = userRepository.findByUserID(userID);
        if (user == null){
            return Result.Error("201","用户不存在");
        }
        else{
            user.url = url;
            userRepository.save(user);
            return Result.Success();
        }
    }

    @RequestMapping("/user/changePasswd")
    @ResponseBody
    public Result changePassword(@RequestParam int userID, String oldPasswd,String newPasswd){
        User user = userRepository.findByUserID(userID);
        if (user == null){
            return Result.Error("201","用户不存在");
        }
        else if (!user.passwd.equals(oldPasswd)){
            return Result.Error("201","旧密码错误");
        }
        else{
            user.passwd = newPasswd;
            userRepository.save(user);
            return Result.Success("修改成功！");
        }
    }

    @GetMapping("/user/getFollowList")
    public Result getFollowList(@RequestParam(value = "userID") int userid) {
        List<User_Expert> ueList = user_expertRepository.findByUserId(userid);
        List<FollowedExpertInfo> list = new ArrayList<>();

        for (User_Expert ue : ueList) {
            Expert e = expertRepository.findByExpertID(ue.expertId);
            list.add(new FollowedExpertInfo(e.getExpertID(), e.getName()));
        }

        return Result.Success(list);
    }
    @RequestMapping("/user/isadmin")
    @ResponseBody
    public Result isAdmin(@RequestParam String token){
        Map map= JwtUtils.parseToken(token);
        boolean is_admin =(int)map.get("is_admin")==1;
        if (is_admin)
            return Result.Success();
        else
            return Result.Error();
    }


    public class Data{
        public String userName;
        public String realName;
        public String email;
        public String phoneNum;
        public String url;
    }

    @RequestMapping("/user/myAchievement")
    @ResponseBody
    public Result getAchievements(@RequestParam String userId){
        User user = userRepository.findByUserID(Integer.valueOf(userId));
        if(user == null || user.expertID == null){
            return Result.Error();
        }
        Expert expert = expertRepository.findByExpertID(user.expertID);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("expertid", ExampleMatcher.GenericPropertyMatcher::exact)
                .withIgnorePaths("id").withIgnorePaths("documentid");
        Document_Expert tmpRelation = new Document_Expert();
        tmpRelation.setExpertID(expert.getExpertID());

        ScholarInfo scholarInfo = new ScholarInfo();

        scholarInfo.name = expert.getName();
        scholarInfo.volume = expert.getViews();
        scholarInfo.scholar_id = expert.getExpertID();
        scholarInfo.affiliate = expert.getOrg();
        scholarInfo.isVerified = false;
        if(expert.getIsVerified() != null && expert.getIsVerified() == 1){
            scholarInfo.isVerified = true;
        }
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("experts",expert.getName()).slop(0);
        boolQueryBuilder.must(queryBuilder);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
//        System.out.println(searchQuery.toString());
        Page<ES_Document> es_documents = es_documentDao.search(searchQuery);
        List<ES_Document> es_documentList = es_documents.toList();
        System.out.println(es_documentList);

        for (ES_Document doc : es_documentList) {
            scholarInfo.achList.add(new Achievement(doc.getTitle(), doc.getCited_quantity(), doc.getId(), doc.getTime()));
        }

        List<User_Expert> list = user_expertRepository.findByUserIdAndExpertId(Integer.parseInt(userId), expert.getExpertID());

        scholarInfo.isFocus = list.size() != 0;

        return Result.Success(scholarInfo);

    }


}

