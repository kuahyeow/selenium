/*
Copyright 2011 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.openqa.selenium.remote.html5;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ExecuteMethod;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Executes the commands to access HTML5 sessionStorage on the remote webdriver server.
 */
public class RemoteSessionStorage implements SessionStorage {
  private final ExecuteMethod executeMethod;

  public RemoteSessionStorage(ExecuteMethod executeMethod) {
    this.executeMethod = executeMethod;
  }

  public String getItem(String key) {
    Map<String, String> args = ImmutableMap.of("key", key);
    return (String) executeMethod.execute(DriverCommand.GET_SESSION_STORAGE_ITEM, args);
  }

  public Set<String> keySet() {
    Collection<String> result = (Collection<String>)
        executeMethod.execute(DriverCommand.GET_SESSION_STORAGE_KEYS, null);
    return new HashSet<String>(result);
  }

  public void setItem(String key, String value) {
    Map<String, String> args = ImmutableMap.of("key", key, "value", value);
    executeMethod.execute(DriverCommand.SET_SESSION_STORAGE_ITEM, args);
  }

  public String removeItem(String key) {
    Map<String, String> args = ImmutableMap.of("key", key);
    return (String) executeMethod.execute(DriverCommand.REMOVE_SESSION_STORAGE_ITEM, args);
  }

  public void clear() {
    executeMethod.execute(DriverCommand.CLEAR_SESSION_STORAGE, null);
  }

  public int size() {
    Object response = executeMethod.execute(DriverCommand.GET_SESSION_STORAGE_SIZE, null);
    return Integer.parseInt(response.toString());
  }
}
