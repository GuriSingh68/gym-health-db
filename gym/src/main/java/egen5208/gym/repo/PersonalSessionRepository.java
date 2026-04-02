package egen5208.gym.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egen5208.gym.model.PersonalSessionView;
import egen5208.gym.model.User;

@Repository
public interface PersonalSessionRepository extends JpaRepository<User, Long> {

  @Query(value = """
      SELECT *
      FROM vw_personal_sessions
      WHERE member_id = :memberId
      ORDER BY session_date, start_time
      """, nativeQuery = true)
  List<PersonalSessionView> getPersonalSessions(@Param("memberId") Long memberId);
}
