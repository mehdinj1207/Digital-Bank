import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AccountsService} from "../services/accounts/accounts.service";
import {catchError, Observable, throwError} from "rxjs";
import {AccountDetails, AccountOperation} from "../model/account.model";
import { Chart } from 'chart.js';

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  accountFormGroup! : FormGroup;
  currentPage : number =0;
  pageSize : number =5;
  accountObservable! : Observable<AccountDetails>
  operationFromGroup! : FormGroup;
  errorMessage! :string ;
  myChart!:Chart
  @ViewChild('lineChart') lineChart!: ElementRef;
  accountOperations: AccountOperation[] = [];

  constructor(private fb : FormBuilder, private accountService : AccountsService) { }

  ngOnInit(): void {
    this.accountFormGroup=this.fb.group({
      accountId : this.fb.control('')
    });
    this.operationFromGroup=this.fb.group({
      operationType : this.fb.control(null),
      amount : this.fb.control(0),
      description : this.fb.control(null),
      accountDestination : this.fb.control(null)
    });
    
  }

  handleSearchAccount() {
    let accountId : string =this.accountFormGroup.value.accountId;
    this.accountObservable=this.accountService.getAccount(accountId,this.currentPage, this.pageSize).pipe(
      catchError(err => {
        this.errorMessage=err.message;
        return throwError(err);
      })
    );
    this.handleAccountOperations();
  }

  gotoPage(page: number) {
    this.currentPage=page;
    this.handleSearchAccount();
  }

  handleAccountOperation() {
    let accountId :string = this.accountFormGroup.value.accountId;
    let operationType=this.operationFromGroup.value.operationType;
    let amount :number =this.operationFromGroup.value.amount;
    let description :string =this.operationFromGroup.value.description;
    let accountDestination :string =this.operationFromGroup.value.accountDestination;
    if(operationType=='DEBIT'){
      this.accountService.debit(accountId, amount,description).subscribe({
        next : (data)=>{
          alert("Success Credit");
          this.operationFromGroup.reset();
          this.handleSearchAccount();
        },
        error : (err)=>{
          console.log(err);
        }
      });
    } else if(operationType=='CREDIT'){
      this.accountService.credit(accountId, amount,description).subscribe({
        next : (data)=>{
          alert("Success Debit");
          this.operationFromGroup.reset();
          this.handleSearchAccount();
        },
        error : (err)=>{
          console.log(err);
        }
      });
    }
    else if(operationType=='TRANSFER'){
      this.accountService.transfer(accountId,accountDestination, amount,description).subscribe({
        next : (data)=>{
          alert("Success Transfer");
          this.operationFromGroup.reset();
          this.handleSearchAccount();
        },
        error : (err)=>{
          console.log(err);
        }
      });

    }
  }
  handleAccountOperations() {
    this.accountService.getAccountOperations(this.accountFormGroup.value.accountId).pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    ).subscribe(data => {
      this.accountOperations = data;
      this.renderLineChart();
      //this.renderLineChart();
      //this.renderRadarChart();
    });
  }
  renderLineChart() {
    if (this.myChart) {
      this.myChart.destroy(); // Détruit le graphique existant
    }
    const ctx = this.lineChart.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Canvas context not available');
      return;
    }

    // Créer des tableaux pour stocker les totaux de crédit et de débit par mois
    const creditTotals = Array(12).fill(0);
    const debitTotals = Array(12).fill(0);

    // Parcourir les opérations pour calculer les totaux
    this.accountOperations.forEach(operation => {
      const monthIndex = new Date(operation.date).getMonth();
      if (operation.type === 'CREDIT') {
        creditTotals[monthIndex] += operation.amount;
      } else if (operation.type === 'DEBIT') {
        debitTotals[monthIndex] += operation.amount;
      }
    });

    // Créer un tableau pour les libellés des mois
    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    // Créer le graphique
    this.myChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: months,
        datasets: [
          {
            label: 'Credit',
            data: creditTotals,
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
          },
          {
            label: 'Debit',
            data: debitTotals,
            fill: false,
            borderColor: 'rgb(255, 99, 132)',
            tension: 0.1
          }
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }
}
