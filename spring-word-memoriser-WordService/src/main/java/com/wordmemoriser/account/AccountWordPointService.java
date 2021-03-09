package com.wordmemoriser.account;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountWordPointService {

    static Logger logger = LogManager.getLogger(AccountWordPointService.class);

    @Autowired
    AccountWordPointRepository accountWordPointRepository;

    public HttpStatus updateAccountWordPoint(Integer remoteId, Integer wordId, Integer point){
        logger.log(Level.getLevel("INTERNAL"),"[updateAccountWordPoint] remoteId:" + remoteId + ", wordId: " + wordId + ", point: " + point);

        Boolean updateResult = false;
        try{
            AccountWordPoint accountWordPoint = accountWordPointRepository.findAll().stream().filter(x -> x.getAccount().getRemoteId()==remoteId && x.getWord().getId() == wordId).findFirst().get();
            int oldVal = accountWordPoint.getPoint();
            accountWordPoint.setPoint(oldVal + point);
            updateResult = true;
            accountWordPointRepository.save(accountWordPoint);
        }catch (Exception e){
            logger.error("[updateAccountWordPoint] exception occured while updating AccountWordPoint: " + e);
            updateResult = false;
        }

        logger.info("[updateWordPoint] Update Result is: " + updateResult);
        return updateResult ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
    }

}
