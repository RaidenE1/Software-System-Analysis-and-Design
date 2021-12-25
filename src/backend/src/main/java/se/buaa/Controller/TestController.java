package se.buaa.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.buaa.Dao.ES_DocumentDao;
import se.buaa.Entity.Document;
import se.buaa.Entity.ESDocument.ES_Document;
import se.buaa.Repository.DocumentRepository;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TestController {
    @Resource
    private ES_DocumentDao es_documentDao;

    @Resource
    private DocumentRepository documentRepository;

    @GetMapping("/test")
    public String insert(){
        List<Document> all = documentRepository.findAll();
        for (Document document : all) {
            ES_Document es_document = ES_Document.builder()
                    .documentid(document.getDocumentID())
                    .title(document.getTitle())
                    .experts(document.getExperts())
                    .keywords(document.getKeywords())
                    .summary(document.getSummary())
                    .cited_quantity(document.getCitedQuantity())
                    .link(document.getLink())
                    .views(document.getView())
                    .build();
            es_documentDao.save(es_document);
        }
        return "";
    }
}
