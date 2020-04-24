/*
 * Copyright 2006 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ifyouwannabecool.impl;

import com.ifyouwannabecool.api.ExclusiveGroupException;
import com.ifyouwannabecool.api.SocialGroupService;
import com.ifyouwannabecool.domain.link.SocialGroup;

import javax.jws.WebService;
import javax.ws.rs.Path;
import java.util.List;

/**
 * @author Ryan Heaton
 */
@WebService (
  endpointInterface = "com.ifyouwannabecool.api.SocialGroupService"
)
@Path ("/group")
public class SocialGroupServiceImpl implements SocialGroupService {

  public SocialGroup createSocialGroup(String groupLeader, List<String> memberIds, boolean exclusive) {
    SocialGroup socialGroup = new SocialGroup();

    //store the social group...
    return socialGroup;
  }

  public boolean addToSocialGroup(String groupId, String personaId) throws ExclusiveGroupException {
    //add the persona to the social group, throw the exception as necessary.
    return true;
  }

  public SocialGroup readGroup(String groupId) {
    SocialGroup group = new SocialGroup();
    group.setId(groupId);
    return group;
  }
}
