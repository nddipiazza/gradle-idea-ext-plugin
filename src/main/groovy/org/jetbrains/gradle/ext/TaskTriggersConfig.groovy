package org.jetbrains.gradle.ext

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap
import groovy.transform.CompileStatic
import org.gradle.api.Task

@CompileStatic
class TaskTriggersConfig implements MapConvertible {

  ListMultimap<String, Task> phaseMap = ArrayListMultimap.create()

  void beforeSync(Task... tasks) {
    phaseMap.putAll("beforeSync", Arrays.asList(tasks))
  }
  void afterSync(Task... tasks) {
    phaseMap.putAll("afterSync", Arrays.asList(tasks))
  }
  void beforeBuild(Task... tasks) {
    phaseMap.putAll("beforeBuild", Arrays.asList(tasks))
  }
  void afterBuild(Task... tasks) {
    phaseMap.putAll("afterBuild", Arrays.asList(tasks))
  }
  void beforeRebuild(Task... tasks) {
    phaseMap.putAll("beforeRebuild", Arrays.asList(tasks))
  }
  void afterRebuild(Task... tasks) {
    phaseMap.putAll("afterRebuild", Arrays.asList(tasks))
  }

  @Override
  Map<String, ?> toMap() {
    def result = [:]
    phaseMap.keySet().each { String phase ->
      List<Task> tasks = phaseMap.get(phase)
      def taskInfos = tasks.collect { task -> ["taskPath" : task.path, "projectPath" : task.project.rootProject.projectDir.path.replaceAll("\\\\", "/")] }
      result.put(phase, taskInfos)
    }
    return result
  }
}