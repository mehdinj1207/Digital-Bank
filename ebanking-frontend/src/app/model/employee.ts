import { Agency } from "./agency"

export interface Employee{ /*class with !*/
  "id": number
  "firstName": string
  "lastName": string
  "cin": string
  "gender": string
  "email":string
  "age": number
  "agency":Agency

}
export interface EmployeeDetails {
    id: string;
    currentPage: number;
    totalPage: number;
    sizePage: number;
    employees: Employee[];
  }