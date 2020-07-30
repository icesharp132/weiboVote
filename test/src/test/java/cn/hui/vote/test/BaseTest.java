package cn.hui.vote.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.hui.vote.app.VoteApplication;

/**
 * @Author: jiangyanze
 * @Description:
 * @Date: Created in 2019/6/20
 * @Modified By:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = VoteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = "classpath:application.yml")
public class BaseTest {
}
