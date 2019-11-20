package com.seer.services;

import com.seer.common.ErrorCodes;
import com.seer.common.StatusCodes;
import com.seer.dao.UserProfileDAO;
import com.seer.dto.UserProfile;
import com.seer.dto.UserProfileInfo;
import com.seer.enums.UserTypeEnum;
import com.seer.exception.DAOException;
import com.seer.exception.SeerException;
import com.seer.services.flex.ResponseData;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Date;
import java.sql.Timestamp;

/*@Service("sessionService")
@RemoteDestination(id="sessionService", source="sessionService")*/
public class SessionService extends AbstractSeerService {

    private Logger logger;

    public SessionService(DriverManagerDataSource dataSource) {
        super(dataSource);
        logger = Logger.getLogger(SessionService.class);
    }

    public ResponseData login(String username, String password) throws SeerException {
        logger.info("LOGIN#" + username + "#" + new Timestamp(System.currentTimeMillis()).toString());
        Subject requestingSubject = SecurityUtils.getSubject();

        try {
            requestingSubject.logout();
            requestingSubject.login(new UsernamePasswordToken(username, password, false));
        }
        catch (AuthenticationException authEx) {
            return new ResponseData(1,"",null);
        }

        Session session = requestingSubject.getSession();
        UserProfileDAO userProfileDAO = new UserProfileDAO(dataSource);
        UserProfile userProfile = null;

        try {
            userProfile = userProfileDAO.getUserProfileForUsername(username);
        }
        catch (DAOException ex) {
            throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
        }

        if (userProfile != null && userProfile.enabled == true && userProfile.role == UserTypeEnum.CUSTOMER) {
            session.setAttribute("loginTime", new Date(System.currentTimeMillis()));
            session.setAttribute("userProfile", userProfile);

            String accessToken = session.getId().toString();

            userProfile.lastLoginTime = new Timestamp(System.currentTimeMillis());
            try {
                userProfileDAO.update(userProfile);
            }
            catch (DAOException ex){
                // do nothing
            }

            return new ResponseData(StatusCodes.OK,"",accessToken);
        }
        else {
            requestingSubject.logout();

            return new ResponseData(1,"",null);
        }
    }

    public ResponseData adminLogin(String username, String password) throws SeerException {
        logger.info("LOGIN#" + username + "#" + new Timestamp(System.currentTimeMillis()).toString());
        Subject requestingSubject = SecurityUtils.getSubject();

        try {
            requestingSubject.logout();
            requestingSubject.login(new UsernamePasswordToken(username, password, false));
        }
        catch (AuthenticationException authEx) {
            return new ResponseData(1,"",null);
        }

        Session session = requestingSubject.getSession();
        UserProfileDAO userProfileDAO = new UserProfileDAO(dataSource);
        UserProfile userProfile = null;

        try {
            userProfile = userProfileDAO.getUserProfileForUsername(username);
        }
        catch (DAOException ex) {
            throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
        }

        if (userProfile != null && userProfile.enabled == true && userProfile.role == UserTypeEnum.ADMIN) {
            session.setAttribute("loginTime", new Date(System.currentTimeMillis()));
            session.setAttribute("userProfile", userProfile);

            String accessToken = session.getId().toString();

            userProfile.lastLoginTime = new Timestamp(System.currentTimeMillis());
            try {
                userProfileDAO.update(userProfile);
            }
            catch (DAOException ex){
                // do nothing
            }

            return new ResponseData(StatusCodes.OK,"",accessToken);
        }
        else {
            requestingSubject.logout();

            return new ResponseData(1,"",null);
        }
    }

    public ResponseData logout() {
        logger.info("LOGOUT#" + new Timestamp(System.currentTimeMillis()).toString());
        Subject requestingSubject = SecurityUtils.getSubject();
        requestingSubject.logout();
        return new ResponseData(0, "Logout success", null);
    }

    public ResponseData getAuthenticatedUser() throws SeerException {
        Subject currentUser = SecurityUtils.getSubject();
        Object principal = currentUser.getPrincipal();
        if (principal != null) {
            String username = principal.toString();
            //currentUser.logout();

            //if (currentUser.isAuthenticated()) {
            if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
                UserProfileDAO dao = new UserProfileDAO(dataSource);
                try {
                    UserProfile userProfile = dao.getUserProfileForUsername(username);
                    if (userProfile != null) {
                        return new ResponseData(0,"", userProfile);
                    }
                    else {
                        return new ResponseData(1,"",null);
                    }
                }
                catch (DAOException ex) {
                    throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
                }
            }
            else {
                return new ResponseData(1,"UserProfile not authenticated",null);
            }
        }
        else {
            return new ResponseData(1,"",null);
        }

    }

    public ResponseData keepAlive(String sessionToken){
        //refresh tokenTime
        return new ResponseData(0,"","snooze...");
    }

    private UserProfile resolveUserForSessionToken(String sessionToken) {
        UserProfile userProfile = new UserProfile();
        //dohvati iz base odgovarajuci UserProfile objekt za zadani sessionToken
        return userProfile;
    }

    public ResponseData getUserProfileInfo(String sessionToken) throws SeerException {
        Session session = getSubject(sessionToken).getSession();
        UserProfile userProfile = (UserProfile) session.getAttribute("userProfile");

        UserProfileDAO dao = new UserProfileDAO(dataSource);
        try {
            UserProfileInfo userProfileInfo = dao.getUserProfileInfoForId(userProfile.id);
            if (userProfileInfo != null) {
                return new ResponseData(StatusCodes.OK,"", userProfileInfo);
            }
            else {
                return new ResponseData(StatusCodes.UNKNOWN_ERROR,"", userProfileInfo);
            }
        }
        catch (DAOException ex) {
            throw new SeerException(ErrorCodes.DATABASE_READ_ERROR, "Read error");
        }
    }
}
