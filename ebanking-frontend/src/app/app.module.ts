import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import {HttpClientModule} from "@angular/common/http";
import {ReactiveFormsModule} from "@angular/forms";
import {NewCustomerComponent} from "./customers/new-customer/new-customer.component";
import { CustomerAccountsComponent } from './customer-accounts/customer-accounts.component';
import { EditCustomerComponent } from './customers/edit-customer/edit-customer.component';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import { FirstHomeComponent } from './first-home/first-home.component';
import { HeaderComponent } from './header/header.component';
import { AboutComponent } from './about/about.component';
import { FooterComponent } from './footer/footer.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { WhyUsComponent } from './why-us/why-us.component';
import { ChartOptions, ChartType, ChartDataset} from 'chart.js';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EmployeeComponent } from './employee/employee.component';
import { EditEmployeeComponent } from './employee/edit-employee/edit-employee.component';
import { NewEmployeeComponent } from './employee/new-employee/new-employee.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    CustomersComponent,
    AccountsComponent,
    NewCustomerComponent,
    CustomerAccountsComponent,
    EditCustomerComponent,
    HomeComponent,
    FirstHomeComponent,
    HeaderComponent,
    AboutComponent,
    FooterComponent,
    ContactUsComponent,
    WhyUsComponent,
    DashboardComponent,
    EmployeeComponent,
    EditEmployeeComponent,
    NewEmployeeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule, //intéragir avec la partie Backend
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
