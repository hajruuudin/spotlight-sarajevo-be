package ba.spotlightsarajevo.service.implementation.service;

import ba.spotlightsarajevo.dao.CommunityRequestDAO;
import ba.spotlightsarajevo.dao.UserDAO;
import ba.spotlightsarajevo.dao.entities.CommunityRequestEntity;
import ba.spotlightsarajevo.dao.entities.UserEntity;
import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestCreate;
import ba.spotlightsarajevo.dao.models.communityrequest.CommunityRequestModel;
import ba.spotlightsarajevo.enums.CommunityRequestType;
import ba.spotlightsarajevo.enums.ObjectType;
import ba.spotlightsarajevo.service.definition.mapper.CommunityRequestMapper;
import ba.spotlightsarajevo.service.definition.service.CommunityRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunityRequestServiceImpl implements CommunityRequestService {
    UserDAO userDAO;
    CommunityRequestDAO communityRequestDAO;
    CommunityRequestMapper communityRequestMapper;

    @Override
    public ResponseEntity<CommunityRequestModel> create(CommunityRequestCreate requestCreate, Principal principal) {
        try {
            UserEntity userEntity = userDAO.findByEmail(principal.getName());

            CommunityRequestEntity entity = new CommunityRequestEntity();

            entity.setUserId(userEntity.getId());
            entity.setUserName(userEntity.getFirstName() + " " + userEntity.getLastName());
            entity.setObjectType(ObjectType.valueOf(requestCreate.getObjectType()));
            entity.setRequestType(CommunityRequestType.valueOf(requestCreate.getRequestType()));
            entity.setRequestName(requestCreate.getRequestName());
            entity.setRequestDescription(requestCreate.getRequestDescription());

            CommunityRequestEntity saved = communityRequestDAO.save(entity);

            return ResponseEntity.ok(communityRequestMapper.entityToDto(saved));
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Community Request Create ERROR");
        }
    }

    @Override
    public ResponseEntity<List<CommunityRequestModel>> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> delete(Integer requestId) {
        return null;
    }
}
