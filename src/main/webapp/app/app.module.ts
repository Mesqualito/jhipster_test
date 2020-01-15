import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { RadiobuttonTestSharedModule } from 'app/shared/shared.module';
import { RadiobuttonTestCoreModule } from 'app/core/core.module';
import { RadiobuttonTestAppRoutingModule } from './app-routing.module';
import { RadiobuttonTestHomeModule } from './home/home.module';
import { RadiobuttonTestEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    RadiobuttonTestSharedModule,
    RadiobuttonTestCoreModule,
    RadiobuttonTestHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    RadiobuttonTestEntityModule,
    RadiobuttonTestAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent]
})
export class RadiobuttonTestAppModule {}
