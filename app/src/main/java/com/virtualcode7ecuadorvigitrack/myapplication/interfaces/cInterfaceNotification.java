package com.virtualcode7ecuadorvigitrack.myapplication.interfaces;

import com.virtualcode7ecuadorvigitrack.myapplication.models.cNotificationSocio;

import java.io.Serializable;

public interface cInterfaceNotification extends Serializable
{
    void no_leido(int position, cNotificationSocio mNotificationSocio);
    void recoveryNotification(int position,cNotificationSocio mNotificationSocio);
}
