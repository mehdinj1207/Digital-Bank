import { Component, OnInit } from '@angular/core';
import { catchError, Observable, of, throwError } from 'rxjs';
import { Employee, EmployeeDetails } from '../model/employee';
import { AccountsService } from '../services/accounts/accounts.service';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
export class EmployeeComponent implements OnInit{
  employees!: Observable<EmployeeDetails>;
  errorMsg: string = '';
  currentPage : number =0;
  pageSize : number =5;
  constructor(private accountService: AccountsService){}
  ngOnInit(): void {
    this.handleEmployees();
    console.log(this.employees)
  }
  
  handleEmployees(){
    this.accountService.getEmployeesPages(this.currentPage, this.pageSize).pipe(
      catchError(err => {
        this.errorMsg = err.message;
        return throwError(err);
      })
    ).subscribe(data => {
      this.employees = of(data);
    });
  }
  gotoPage(page: number) {
    this.currentPage=page;
    this.handleEmployees();
  }
  
}
