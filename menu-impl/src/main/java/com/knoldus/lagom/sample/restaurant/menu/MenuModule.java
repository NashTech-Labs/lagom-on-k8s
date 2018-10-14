/*
 * Copyright (C) 2018 Knoldus Inc. <https://www.knoldus.com/home.knol>
 */
package com.knoldus.lagom.sample.restaurant.menu;

import akka.actor.ActorSystem;
import akka.management.AkkaManagement$;
import akka.management.cluster.bootstrap.ClusterBootstrap$;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;
import play.Application;

public final class MenuModule extends AbstractModule implements ServiceGuiceSupport {

    @Override
    protected void configure() {
        bindService(MenuService.class, MenuServiceImpl.class);
        bind(AkkaManagerAndClusterStart.class).asEagerSingleton();
    }

    class AkkaManagerAndClusterStart {
        @Inject
        public AkkaManagerAndClusterStart(final Application application, final ActorSystem actorSystem) {
            if(application.isProd()) {
                AkkaManagement$.MODULE$.get(actorSystem).start();
                ClusterBootstrap$.MODULE$.get(actorSystem).start();
            }
        }
    }

}
