/**
 * This file is part of DrOntoAPI.
 *
 * Copyright 2014 Ionel Virgil Pop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ionelvirgilpop.drontoapi.pitfallmanager;

/**
 * @author Ionel Virgil Pop
 *
 */
public class UnexpectedError {

    private String title = "";
    private String Message = "";

    protected UnexpectedError() {
    }

    protected UnexpectedError(String title, String message) {
        setTitle(title);
        setMessage(message);
    }

    public String getMessage() {
        return Message;
    }

    protected void setMessage(String message) {
        Message = message;
    }

    public String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

}
