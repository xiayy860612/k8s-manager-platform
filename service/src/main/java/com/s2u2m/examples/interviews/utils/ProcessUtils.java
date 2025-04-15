package com.s2u2m.examples.interviews.utils;

import com.s2u2m.examples.interviews.error.ErrorCode;
import com.s2u2m.examples.interviews.error.PlatformException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.springframework.http.HttpStatus;

public class ProcessUtils {

  public static String runCommand(List<String> cmd) throws IOException, InterruptedException {
    ProcessBuilder builder = new ProcessBuilder();
    builder.command(cmd);
    Process process = builder.start();

    StringBuilder output = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line);
      }
    }

    int exitCode = process.waitFor();
    String result = output.toString();
    if (exitCode != 0) {
      throw new PlatformException(
          HttpStatus.SERVICE_UNAVAILABLE, ErrorCode.PROVIDER_SERVICE_NOT_AVAILABLE, result);
    }

    return output.toString();
  }
}
