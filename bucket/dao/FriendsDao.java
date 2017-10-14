package evolution.dao;

import evolution.common.FriendStatusEnum;
import evolution.model.friend.Friends;
import evolution.model.user.UserLight;
import evolution.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 26.03.2017.
 */
@Service
public class FriendsDao {

    private static final String COUNT_FRIENDS = "select count(1) from Friends f where f.user.id = :user_id and f.status = " + FriendStatusEnum.PROGRESS.getId();

    private static final String COUNT_FOLLOWERS = "select count(1) from Friends f where f.user.id = :user_id and f.status = " + FriendStatusEnum.FOLLOWER.getId();

    private static final String COUNT_REQUESTS = "select count(1) from Friends f where f.user.id = :user_id and f.status = " + FriendStatusEnum.REQUEST.getId();

    private static final String CHECK_FRIENDS = "select 1 from Friends f where (f.user.id =:id1 and f.friend.id =:id2 and f.status =:status) " +
            "or (f.user.id =:id2 and f.friend.id =:id1 and f.status =:status )";

//    private static final String FIND_ALL_FRIENDS = "select new Friends " +
//            "(uf.id, uf.firstName, uf.lastName," +
//            " uu.id, uu.firstName, uu.lastName) from UserLight uf" +
//            " join Friends ff on uf.id = ff.friend.id " +
//            " join UserLight uu on uu.id = ff.user.id " +
//            " where ff.user.id = :id ";

    private static final String FIND_ALL_FRIENDS = "select ff from Friends ff where ff.user.id = :id ";

    private static final String USER_JOIN_FRIEND = "select new Friends " +
            "(uf.id, uf.firstName, uf.lastName) " +
            " from StandardUser uf" +
            " join Friends ff on uf.id = ff.friend.id " +
            " where ff.user.id = :id ";

    private static final String SET_STATUS_FRIEND = "update Friends set status = :status where user.id = :u and friend.id = :f and status =:s";

    private static final String DELETE_REQUEST_FRIEND = "delete from Friends where (user.id = :u and friend.id = :f) \n" +
            "or (user.id = :f and friend.id = :u)";

    private static final String MORE_FRIEND = USER_JOIN_FRIEND + " and ff.status = " + FriendStatusEnum.PROGRESS.getId();

    private static final String MORE_FOLLOWER = USER_JOIN_FRIEND + " and ff.status = " + FriendStatusEnum.FOLLOWER.getId();

    private static final String MORE_REQUEST = USER_JOIN_FRIEND + " and ff.status = " + FriendStatusEnum.REQUEST.getId();

    private static final String FIND_FRIEND = FIND_ALL_FRIENDS + " and ff.status = " + FriendStatusEnum.PROGRESS.getId();

    private static final String FIND_FOLLOWER = FIND_ALL_FRIENDS + " and ff.status = " + FriendStatusEnum.FOLLOWER.getId();

    private static final String FIND_REQUEST = FIND_ALL_FRIENDS + " and ff.status = " + FriendStatusEnum.REQUEST.getId();

    private static final String RANDOM_FRIENDS = "select new Friends(uf.id, uf.firstName) from " +
            " Friends f " +
            " join f.friend as uf " +
            " where f.user.id = :user_id " +
            " and f.status = " + FriendStatusEnum.PROGRESS.getId() +
            " order by rand() ";

//    private static final String FIND_USER_AND_FRIEND_STATUS = " select new Friends(u.id, u.firstName, u.lastName, f.status)" +
//            " from UserLight u " +
//            " left join Friends f on u.id = f.friend.id and f.user.id = :authUserId " +
//            " where u.id = :id ";

    private static final String FIND_USER_AND_FRIEND_STATUS = " FROM Friends f, StandardUser u " +
            " where u.id = f.friend.id and f.user.id = :authUserId" +
            " and u.id = :id ";


    @Autowired
    private FriendRepository friendRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<User> randomFriends(Long userId, int limit) {
        Query query = entityManager.createQuery(RANDOM_FRIENDS);
        query.setParameter("user_id", userId);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional
    public Long countFriends(Long userId) {
        Query query = entityManager.createQuery(COUNT_FRIENDS);
        query.setParameter("user_id", userId);
        return (Long) query.getSingleResult();
    }

    @Transactional
    public Long countFollowers(Long userId) {
        Query query = entityManager.createQuery(COUNT_FOLLOWERS);
        query.setParameter("user_id", userId);
        return (Long) query.getSingleResult();
    }

    @Transactional
    public Long countRequests(Long userId) {
        Query query = entityManager.createQuery(COUNT_REQUESTS);
        query.setParameter("user_id", userId);
        return (Long) query.getSingleResult();
    }

    @Transactional
    public Map<String, Long> countForFriends(Long userId) {
        Map<String, Long> map = new HashMap<>();
        Query query;

        query = entityManager.createQuery(COUNT_FRIENDS);
        query.setParameter("user_id", userId);
        map.put(FriendStatusEnum.PROGRESS.toString(), (Long) query.getSingleResult());

        query = entityManager.createQuery(COUNT_FOLLOWERS);
        query.setParameter("user_id", userId);
        map.put(FriendStatusEnum.FOLLOWER.toString(), (Long) query.getSingleResult());

        query = entityManager.createQuery(COUNT_REQUESTS);
        query.setParameter("user_id", userId);
        map.put(FriendStatusEnum.REQUEST.toString(), (Long) query.getSingleResult());

        return map;
    }

    @Transactional
    public void friendRequest(long authUserId, long id2) {
        UserLight authUser = new UserLight(authUserId);
        UserLight user2 = new UserLight(id2);
        Friends request = new Friends(authUser, user2, FriendStatusEnum.REQUEST.getId());
        Friends follower = new Friends(user2, authUser, FriendStatusEnum.FOLLOWER.getId());
        entityManager.persist(follower);
        entityManager.persist(request);
    }

    @Transactional
    public void deleteFriend(long authUserId, long id2) {
        Query query = entityManager.createQuery(SET_STATUS_FRIEND);
        query.setParameter("status", FriendStatusEnum.FOLLOWER.getId());
        query.setParameter("u", authUserId);
        query.setParameter("f", id2);
        query.setParameter("s", FriendStatusEnum.PROGRESS.getId());
        query.executeUpdate();

        query = entityManager.createQuery(SET_STATUS_FRIEND);
        query.setParameter("status", FriendStatusEnum.REQUEST.getId());
        query.setParameter("u", id2);
        query.setParameter("f", authUserId);
        query.setParameter("s", FriendStatusEnum.PROGRESS.getId());
        query.executeUpdate();
    }

    @Transactional
    public void acceptFriend(long authUserId, long id2) {
        Query query = entityManager.createQuery(SET_STATUS_FRIEND);
        query.setParameter("status", FriendStatusEnum.PROGRESS.getId());
        query.setParameter("u", authUserId);
        query.setParameter("f", id2);
        query.setParameter("s", FriendStatusEnum.FOLLOWER.getId());
        query.executeUpdate();

        query = entityManager.createQuery(SET_STATUS_FRIEND);
        query.setParameter("status", FriendStatusEnum.PROGRESS.getId());
        query.setParameter("u", id2);
        query.setParameter("f", authUserId);
        query.setParameter("s", FriendStatusEnum.REQUEST.getId());
        query.executeUpdate();
    }

    @Transactional
    public void deleteRequest(long authUserId, long id2) {
        Query query = entityManager.createQuery(DELETE_REQUEST_FRIEND);
        query.setParameter("u", authUserId);
        query.setParameter("f", id2);
        query.executeUpdate();
    }

//    @Transactional
    public Friends findUserAndFriendStatus(Long authUserId, Long id) {
        Query query = entityManager.createQuery(FIND_USER_AND_FRIEND_STATUS);
        query.setParameter("authUserId", authUserId);
        query.setParameter("id", id);
        return (Friends) query.getSingleResult();
//        return friendRepository.findUserAndFriendStatus(authUserId, id);
    }

    @Transactional
    public List<Friends> findFriend(long authUserId, int limit, int offset) {
        Query query = entityManager.createQuery(FIND_FRIEND);
        query.setParameter("id", authUserId);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Transactional
    public Map<String, List<Friends>> friend(long authUserId, int limit, int offset) {
        Map<String, List<Friends>> map = new HashMap<>();
        map.put("progress", findFriend(authUserId, limit, offset));
        map.put("follower", findFollower(authUserId, limit, offset));
        map.put("sendRequest", findRequest(authUserId, limit, offset));
        return map;
    }

    @Transactional
    public Map<String, List<Friends>> friendFollower(long authUserId, int limit, int offset) {
        Map<String, List<Friends>> map = new HashMap<>();
        map.put("progress", findFriend(authUserId, limit, offset));
        map.put("follower", findFollower(authUserId, limit, offset));
        return map;
    }

    @Transactional
    public List<Friends> findFollower(long authUserId, int limit, int offset) {
        Query query = entityManager.createQuery(FIND_FOLLOWER);
        query.setParameter("id", authUserId);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Transactional
    public List<Friends> findRequest(long authUserId, int limit, int offset) {
        Query query = entityManager.createQuery(FIND_REQUEST);
        query.setParameter("id", authUserId);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Transactional
    public List<Friends> moreFriend(long authUserId, int limit, int offset) {
        Query query = entityManager.createQuery(MORE_FRIEND);
        query.setParameter("id", authUserId);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Transactional
    public List<Friends> moreFollower(long authUserId, int limit, int offset) {
        Query query = entityManager.createQuery(MORE_FOLLOWER);
        query.setParameter("id", authUserId);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Transactional
    public List<Friends> moreRequest(long authUserId, int limit, int offset) {
        Query query = entityManager.createQuery(MORE_REQUEST);
        query.setParameter("id", authUserId);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        return query.getResultList();
    }

    @Transactional
    public boolean checkFriends(long authUserId, long id2) {
        Query query = entityManager.createQuery(CHECK_FRIENDS);
        query.setParameter("id1", authUserId);
        query.setParameter("id2", id2);
        query.setParameter("status", FriendStatusEnum.PROGRESS.getId());
        if (query.getResultList().size() == 2)
            return true;
        return false;
    }
}
