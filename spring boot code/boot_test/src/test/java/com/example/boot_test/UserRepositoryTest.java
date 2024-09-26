package com.example.boot_test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.boot_test.model.User;
import com.example.boot_test.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
	
	// 의존성 주입을 할 때
	// 생성자 주입 X -> Junit이 기본 생성자로 테스트.
	@Autowired
	private UserRepository userRepository;
	
	@Test
	void testCRUD() {
		// 생성 (Create)
		User user = new User("김자바",
				"kim@java.com");
		// name, email을 넣어서 만든 생성자일 경우
		// id가 null인지 (지정 안해줬으니까..)
		assertThat(user.getId())
			.isNull();
		User savedUser = userRepository
				.save(user);
		// id가 부여된 user가 나오게 됨.
		assertThat(savedUser.getId())
			.isNotNull();
		// 기본 생성자로 만들어진 유저
		User user2 = new User();
		// name, email도 비어있을 경우
		assertThrows(DataIntegrityViolationException.class,
				() -> userRepository.save(user2));
		// 중복된 email
		User user3 = new User("김자바",
				"kim@java.com");
		assertThrows(DataIntegrityViolationException.class,
				() -> userRepository.save(user3));
		// ------------------------------------
		// 읽기 (Read)
		User foundUser = userRepository
				.findById(savedUser.getId())
				.orElse(null);
		assertThat(foundUser).isNotNull();
		assertThat(foundUser.getEmail())
			.contains("java");
		assertThat(foundUser.getName())
			.isEqualTo("김자바");
		// 수정 (Update)
		foundUser.setName("김스프링");
		User updatedUser = userRepository
				.save(foundUser);
		assertThat(updatedUser.getName())
			.isNotEqualTo("김자바");
		assertThat(updatedUser.getName())
			.isEqualTo("김스프링");
		assertThat(updatedUser.getId())
			.isEqualTo(foundUser.getId());
		// 삭제 (Delete)
		// userRepository.delete(updatedUser);
		userRepository.deleteById(updatedUser.getId());
		assertThat(userRepository
				.findById(updatedUser.getId())).isEmpty();
	}
	
    @BeforeEach
    void setUp() {
        // 20개의 샘플 데이터 생성
        for (int i = 1; i <= 10; i++) {
            userRepository.save(new User("사용자" + i, "user" + i + "@java.com"));
        }
        for (int i = 11; i <= 20; i++) {
            userRepository.save(new User("사용자" + i, "user" + i + "@example.com"));
        }
    }

    @Test
    void testFindTop5ByEmailContainingOrderByEmailDesc() {
        // "java.com"이 포함된 상위 5개의 사용자 찾기
        List<User> users = userRepository.findTop5ByEmailContainingOrderByEmailDesc("java.com");

        // 반환된 사용자 리스트 크기 검증
        assertThat(users).hasSize(5);

        // 이메일이 "java.com"을 포함하는지 검증
        for (User user : users) {
            assertThat(user.getEmail()).contains("java.com");
        }

        // 이메일이 역순으로 정렬되었는지 검증
        assertThat(users.get(0).getEmail()).isGreaterThan(users.get(1).getEmail());
        assertThat(users.get(1).getEmail()).isGreaterThan(users.get(2).getEmail());
        assertThat(users.get(2).getEmail()).isGreaterThan(users.get(3).getEmail());
        assertThat(users.get(3).getEmail()).isGreaterThan(users.get(4).getEmail());
    }
}
