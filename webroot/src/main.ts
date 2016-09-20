import './polyfills.ts';

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { environment } from './environments/environment';
import { AppModule } from './app/';
import * as Keycloak from 'keycloak-js';

if (environment.production) {
  enableProdMode();
}



let keycloak = Keycloak('keycloak/keycloak.json');
window['_keycloak'] = keycloak;

window['_keycloak'].init(
    {onLoad: 'login-required'}
)
    .success(function (authenticated) {

      if (!authenticated) {
        window.location.reload();
      }

      // refresh login
      setInterval(function () {

        keycloak.updateToken(70).success(function (refreshed) {
          if (refreshed) {
            console.log('Token refreshed');
          } else {
            console.log('Token not refreshed, valid for '
                + Math.round(keycloak.tokenParsed.exp + keycloak.timeSkew - new Date().getTime() / 1000) + ' seconds');
          }
        }).error(function () {
          console.error('Failed to refresh token');
        });

      }, 60000);

      console.log("Loading...");

      platformBrowserDynamic().bootstrapModule(AppModule);

    });


