import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomersComponent } from "./customers/customers.component";
import { AccountsComponent } from "./accounts/accounts.component";
import { NewCustomerComponent } from "./customers/new-customer/new-customer.component";
import { CustomerAccountsComponent } from "./customer-accounts/customer-accounts.component";
import { EditCustomerComponent } from "./customers/edit-customer/edit-customer.component";
import { LoginComponent } from "./login/login.component";
import { NavbarComponent } from "./navbar/navbar.component";
import { HomeComponent } from "./home/home.component";
import { FirstHomeComponent } from './first-home/first-home.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { WhyUsComponent } from './why-us/why-us.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { EmployeeComponent } from './employee/employee.component';

const routes: Routes = [
  { path: "login", component: LoginComponent },
  { path: "contact", component: ContactUsComponent },
  { path: "services", component: WhyUsComponent },
  {
    path: "", component: FirstHomeComponent
  },
  {
    path: "admin", component: NavbarComponent, //user
    // canActivate : [AuthenticationGuard],
    children: [
      { path: "home", component: HomeComponent },
      { path: "customers", component: CustomersComponent },
      { path: "dashboard", component: DashboardComponent },
      { path: "employee", component: EmployeeComponent },
      { path: "accounts", component: AccountsComponent },
      { path: "new-customer", component: NewCustomerComponent },
      { path: "edit-customer/:id", component: EditCustomerComponent },
      { path: "customer-accounts/:id", component: CustomerAccountsComponent }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
