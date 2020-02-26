package com.seer.services;

import com.seer.common.ErrorCodes;
import com.seer.common.StatusCodes;
import com.seer.dao.*;
import com.seer.dto.Apartment;
import com.seer.dto.Message;
import com.seer.dto.UserProfile;
import com.seer.enums.UserTypeEnum;
import com.seer.exception.DAOException;
import com.seer.exception.SeerException;
import com.seer.services.flex.ResponseData;
import org.apache.shiro.session.Session;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Collection;

public class AdminService extends AbstractSeerService {

    public AdminService(DriverManagerDataSource dataSource) {
        super(dataSource);
    }


    ///////////////////////////////
    ///// API FOR ADMIN APP ///////
    ///////////////////////////////



//    public ResponseData getApartments(String sessionToken)/* throws SeerException */ {
//        Session session = getSubject(sessionToken).getSession();
//        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
//
//        if (userProfile != null && userProfile.role == UserTypeEnum.ADMIN){
//            ApartmentDAO dao = new ApartmentDAO(dataSource);
//            try {
//                Collection<Apartment> res = dao.getApartments();
//                if (res != null) {
//                    return new ResponseData(StatusCodes.OK, "", res);
//                } else {
//                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
//                }
//            } catch (DAOException ex) {
//                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
//            }
//        }else{
//            return new ResponseData(1,"",null);
//        }
//    }
//
//    public ResponseData getMessages(String sessionToken) throws SeerException {
//        Session session = getSubject(sessionToken).getSession();
//        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");
//
//        if (userProfile != null && userProfile.role == UserTypeEnum.ADMIN){
//            MessageDAO dao = new MessageDAO(dataSource);
//            try {
//                Collection<Message> res = dao.getMessages();
//                if (res != null) {
//                    return new ResponseData(StatusCodes.OK, "", res);
//                } else {
//                    return new ResponseData(StatusCodes.UNKNOWN_ERROR, "", res);
//                }
//            } catch (DAOException ex) {
//                throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
//            }
//        }else{
//            return new ResponseData(1,"",null);
//        }
//    }

}
