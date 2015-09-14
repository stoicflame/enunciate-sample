package com.ifyouwannabecool.api;

import com.ifyouwannabecool.domain.link.SocialGroup;

import javax.ws.rs.*;
import java.util.Collection;
import java.util.List;

/**
 * @author Ryan Heaton
 */
public interface SocialGroupService {

  @Path("/{groupId}")
  @GET
  SocialGroup readGroup(@PathParam("groupId") String groupId);

  @POST
  SocialGroup createSocialGroup(@QueryParam("leader") String groupLeader,
                                @QueryParam("member") List<String> memberIds,
                                @QueryParam("exclusive") boolean exclusive);

  @Path("/{groupId}")
  @POST
  boolean addToSocialGroup(@PathParam ("groupId") String groupId,
                           @QueryParam ("member") String personaId) throws ExclusiveGroupException;

}
