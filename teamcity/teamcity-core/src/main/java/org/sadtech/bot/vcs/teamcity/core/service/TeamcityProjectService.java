package org.sadtech.bot.vcs.teamcity.core.service;

import lombok.NonNull;
import org.sadtech.basic.context.service.SimpleManagerService;
import org.sadtech.bot.vcs.teamcity.core.domain.entity.TeamcityProject;

import java.util.List;
import java.util.Set;

/**
 * // TODO: 21.09.2020 Добавить описание.
 *
 * @author upagge 21.09.2020
 */
public interface TeamcityProjectService extends SimpleManagerService<TeamcityProject, String> {

    List<String> exists(@NonNull Set<String> projectIds);

}
