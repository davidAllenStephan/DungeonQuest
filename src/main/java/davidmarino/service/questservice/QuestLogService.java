package davidmarino.service.questservice;

import davidmarino.model.questmodels.QuestLogCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestLogService {

    @Autowired
    private static QuestLogCollection questLogCollection;


}
