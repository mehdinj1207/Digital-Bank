import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountDetails, AccountOperation} from "../../model/account.model";
import { Employee, EmployeeDetails } from 'src/app/model/employee';
import { Agency } from 'src/app/model/agency';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  private backendHost="http://localhost:8090"
  constructor(private http : HttpClient) { }

  public getAccount(accountId : string, page : number, size : number):Observable<AccountDetails>{
    return this.http.get<AccountDetails>(this.backendHost+"/accounts/"+accountId+"/pageOperations?page="+page+"&size="+size);
  }
  public getOperations():Observable<AccountOperation[]>{
    return this.http.get<AccountOperation[]>(this.backendHost+"/operations");
  }
  public getAccountOperations(accountId: string):Observable<AccountOperation[]>{
    return this.http.get<AccountOperation[]>(this.backendHost+"/operations/"+accountId);
  }
  public getTotalAccounts():Observable<number>{
    return this.http.get<number>(this.backendHost+"/accounts/total");
  }
  public getTotalCustomers():Observable<number>{
    return this.http.get<number>(this.backendHost+"/customers/total");
  }
  public debit(accountId : string, amount : number, description:string){
    let data={accountId : accountId, amount : amount, description : description}
    return this.http.post(this.backendHost+"/accounts/debit",data);
  }
  public credit(accountId : string, amount : number, description:string){
    let data={accountId : accountId, amount : amount, description : description}
    return this.http.post(this.backendHost+"/accounts/credit",data);
  }
  public transfer(accountSource: string,accountDestination: string, amount : number, description:string){
    let data={accountSource, accountDestination, amount, description }
    return this.http.post(this.backendHost+"/accounts/transfer",data);
  }


  public getEmployees(): Observable<Array<Employee>> {
    return this.http.get<Array<Employee>>(this.backendHost + "/employees")
  }
  public getEmployeesPages(page : number, size : number):Observable<EmployeeDetails>{
    return this.http.get<EmployeeDetails>(this.backendHost+"/employees/pages?page="+page+"&size="+size);
  }
  public getTotalEmployees():Observable<number>{
    return this.http.get<number>(this.backendHost+"/employees/total");
  }
  public getEmployee(id: number): Observable<Employee> {
    return this.http.get<Employee>(this.backendHost + "/employees/" + id)
  }


  public saveEmployee(employee: Employee): Observable<Employee> { // Frontend<-->Backend
    return this.http.post<Employee>(this.backendHost + "/employees/add", employee);
  }

  public deleteEmployee(id: number) {
    return this.http.delete(this.backendHost + "/employees/delete/" + id);
  }
  public getAgencies(): Observable<Array<Agency>> {
    return this.http.get<Array<Agency>>(this.backendHost + "/agencies")
  }

  public getAgency(id: number): Observable<Agency> {
    return this.http.get<Agency>(this.backendHost + "/agencies/" + id)
  }


  public saveAgency(agency: Agency): Observable<Agency> { // Frontend<-->Backend
    return this.http.post<Agency>(this.backendHost + "/agencies/add", agency);
  }

  public deleteAgency(id: number) {
    return this.http.delete(this.backendHost + "/agencies/delete/" + id);
  }
}
