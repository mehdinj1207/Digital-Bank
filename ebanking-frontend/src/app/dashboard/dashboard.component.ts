import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { catchError, Observable, throwError } from 'rxjs';
import { AccountOperation } from '../model/account.model';
import { AccountsService } from '../services/accounts/accounts.service';
import Chart from 'chart.js/auto';
import { Employee } from '../model/employee';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  @ViewChild('myChart') myChart!: ElementRef;
  @ViewChild('pieChart') pieChart!: ElementRef;
  @ViewChild('pieChartEmployee') pieChartEmployee!: ElementRef;
  @ViewChild('lineChart') lineChart!: ElementRef;
  accountOperations: AccountOperation[] = [];
  employees: Employee[] = [];
  totalAccounts: number = 0;
  totalCustomers: number = 0;
  totalEmployees: number = 0;
  errorMessage: string = '';

  constructor(private accountService: AccountsService) { }
  ngOnInit(): void {
    this.handleTotalAccounts();
    this.handleTotalCustomers();
    this.handleTotalEmployees();
    


  }

  ngAfterViewInit(): void {
    this.handleSearchOperations();
  }
  handleTotalAccounts() {
    this.accountService.getTotalAccounts().subscribe(data => {
      this.totalAccounts = data;

    });
  }
  handleTotalEmployees(){
    this.accountService.getTotalEmployees().subscribe(data => {
      this.totalEmployees = data;

    });
  }

  handleTotalCustomers() {
    this.accountService.getTotalCustomers().subscribe(data => {
      this.totalCustomers = data;

    });
  }

  handleSearchOperations() {
    this.accountService.getOperations().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(err);
      })
    ).subscribe(data => {
      this.accountOperations = data;
      this.accountService.getEmployees().pipe(
        catchError(err => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      ).subscribe(data => {
        this.employees = data;
        
      this.createPieChartEmployee();
      });
      this.renderChart();
      this.createPieChart();
      this.renderLineChart();
      //this.renderLineChart();
      //this.renderRadarChart();
    });
  }

  renderChart() {
    const ctx = this.myChart.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Canvas context not available');
      return;
    }

    // Regrouper les opérations par jour
    const groupedOperations: { [date: string]: { credit: number, debit: number } } = {};
    this.accountOperations.forEach(operation => {
      const date = new Date(operation.date).toDateString();
      if (!groupedOperations[date]) {
        groupedOperations[date] = { credit: 0, debit: 0 };
      }
      if (operation.type === 'CREDIT') {
        groupedOperations[date].credit += operation.amount;
      } else if (operation.type === 'DEBIT') {
        groupedOperations[date].debit += operation.amount;
      }
    });

    // Extraire les dates et les totaux de crédit et de débit
    const dates = Object.keys(groupedOperations);
    const credits = dates.map(date => groupedOperations[date].credit);
    const debits = dates.map(date => groupedOperations[date].debit);

    const myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: dates,
        datasets: [{
          label: 'Crédit',
          data: credits,
          backgroundColor: 'rgba(54, 162, 235, 0.6)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1
        }, {
          label: 'Débit',
          data: debits,
          backgroundColor: 'rgba(255, 99, 132, 0.6)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
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
  createPieChart() {
    const operationTypes: { [key: string]: number } = {};

    this.accountOperations.forEach(operation => {
      // Type assertion to inform TypeScript about the type of operation.type
      const type = operation.type as string;

      if (type in operationTypes) {
        operationTypes[type] += operation.amount;
      } else {
        operationTypes[type] = operation.amount;
      }
    });

    const labels = Object.keys(operationTypes);
    const amounts = Object.values(operationTypes);

    // Creating the pie chart
    const ctx = this.pieChart.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Canvas context not available');
      return;
    }
    const pieChart = new Chart(ctx, {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [{
          data: amounts,
          backgroundColor: [
            'rgba(255, 99, 132, 0.6)',
            'rgba(54, 162, 235, 0.6)',
            'rgba(255, 206, 86, 0.6)',
            'rgba(75, 192, 192, 0.6)',
            'rgba(153, 102, 255, 0.6)',
            'rgba(255, 159, 64, 0.6)'
          ],
          borderWidth: 1
        }]
      },

    });
  }


  createPieChartEmployee() {
    // Create a map to store the count of employees for each agency
    const agencyEmployeeCountMap: { [key: string]: number } = {};

    // Count the number of employees for each agency
    this.employees.forEach(employee => {
      const agencyName = employee.agency.location; // Assuming 'name' is the property for agency name
      if (agencyName in agencyEmployeeCountMap) {
        agencyEmployeeCountMap[agencyName]++;
      } else {
        agencyEmployeeCountMap[agencyName] = 1;
      }
    });

    // Extract labels and amounts from the map
    const labels = Object.keys(agencyEmployeeCountMap);
    const amounts = Object.values(agencyEmployeeCountMap);

    // Create the pie chart
    const ctx = this.pieChartEmployee.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Canvas context not available');
      return;
    }
    const pieChartEmployee = new Chart(ctx, {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [{
          data: amounts,
          backgroundColor: [
            'rgba(255, 99, 132, 0.6)',
            'rgba(54, 162, 235, 0.6)',
            'rgba(255, 206, 86, 0.6)',
            'rgba(75, 192, 192, 0.6)',
            'rgba(153, 102, 255, 0.6)',
            'rgba(255, 159, 64, 0.6)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        // Add options as needed
      }
    });
  }

  /*
  renderChartBar() {
    const ctx = this.barChart.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Canvas context not available');
      return;
    }

    const myChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: this.accountOperations.map(operation => operation.date),
        datasets: [{
          label: 'Amount',
          data: this.accountOperations.map(operation => operation.amount),
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
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
  */
  renderLineChart() {
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
    const myChart = new Chart(ctx, {
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
  }/*
  renderRadarChart() {
    const ctx = this.radarChart.nativeElement.getContext('2d');
    if (!ctx) {
      console.error('Canvas context not available');
      return;
    }

    const myChart = new Chart(ctx, {
      type: 'radar',
      data: {
        labels: this.accountOperations.map(operation => operation.type),
        datasets: [{
          label: 'Amount',
          data: this.accountOperations.map(operation => operation.amount),
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
      },
      options: {
        scales: {
          r: {
            beginAtZero: true
          }
        }
      }
    });
  }*/
}
