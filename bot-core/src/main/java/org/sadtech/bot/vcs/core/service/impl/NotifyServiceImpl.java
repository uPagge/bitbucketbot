package org.sadtech.bot.vcs.core.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.sadtech.bot.vcs.core.domain.entity.NotifySetting;
import org.sadtech.bot.vcs.core.domain.notify.Notify;
import org.sadtech.bot.vcs.core.repository.NotifySettingRepository;
import org.sadtech.bot.vcs.core.service.MessageSendService;
import org.sadtech.bot.vcs.core.service.NotifyService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private final NotifySettingRepository settingRepository;

    private final MessageSendService messageSendService;

    @Override
    public <T extends Notify> void send(T notify) {
        final Set<String> recipientLogins = settingRepository.isNotification(notify.getLogins());
        notify.setLogins(recipientLogins);
        messageSendService.send(notify);
    }

    @Override
    public void saveSettings(@NonNull NotifySetting setting) {
        settingRepository.save(setting);
    }

    @Override
    public Optional<NotifySetting> getSetting(@NonNull String login) {
        return settingRepository.findById(login);
    }

}
