package com.ko.mediate.HC.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.jupiter.api.Nested;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

@TestConfiguration
public class LocalRedisConfig {
  private int redisPort = 6378;
  private RedisServer redisServer;

  @PostConstruct
  public void redisServer() throws IOException {
    int port = isRedisRunning() ? findAvailablePort() : redisPort;
    redisServer = new RedisServer(port);
    redisServer.start();
  }

  @Nested
  class LocalTestRedisClientConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
      return new LettuceConnectionFactory("127.0.0.1", redisPort);
    }
  }

  @PreDestroy
  public void stopRedis() {
    if (redisServer != null) {
      redisServer.stop();
    }
  }

  /** Embedded Redis가 현재 실행중인지 확인 */
  private boolean isRedisRunning() throws IOException {
    return isRunning(executeGrepProcessCommand(redisPort));
  }

  /** 현재 PC/서버에서 사용가능한 포트 조회 */
  public int findAvailablePort() throws IOException {

    for (int port = 10000; port <= 65535; port++) {
      Process process = executeGrepProcessCommand(port);
      if (!isRunning(process)) {
        return port;
      }
    }

    throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
  }

  /** 해당 port를 사용중인 프로세스 확인하는 sh 실행 */
  private Process executeGrepProcessCommand(int port) throws IOException {
    String command;
    String[] shell;
    String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT); // 현재 VM이 실행 중인 OS 이름 얻기
    if (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0 || osName.indexOf("aix") > 0) {
      command = String.format("netstat -nat | grep LISTEN|grep %d", port);
      shell = new String[] {"/bin/sh", "-c", command};
    } else {
      command = String.format("netstat -nao | find \"LISTEN\" | find \"%d\"", port);
      shell = new String[] {"cmd.exe", "/y", "/c", command};
    }
    return Runtime.getRuntime().exec(shell);
  }

  /** 해당 Process가 현재 실행중인지 확인 */
  private boolean isRunning(Process process) {
    String line;
    StringBuilder pidInfo = new StringBuilder();

    try (BufferedReader input =
        new BufferedReader(new InputStreamReader(process.getInputStream()))) {

      while ((line = input.readLine()) != null) {
        pidInfo.append(line);
      }

    } catch (Exception e) {
    }

    return StringUtils.hasText(pidInfo.toString());
  }
}
