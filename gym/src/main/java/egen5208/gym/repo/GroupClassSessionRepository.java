package egen5208.gym.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.GroupClassSessionView;
import egen5208.gym.model.User;

@Repository
public interface GroupClassSessionRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT *
            FROM vw_group_class_sessions
            WHERE member_id = :memberId
            ORDER BY date, start_time
            """, nativeQuery = true)
    List<GroupClassSessionView> getGroupSessions(@Param("memberId") Long memberId);
}