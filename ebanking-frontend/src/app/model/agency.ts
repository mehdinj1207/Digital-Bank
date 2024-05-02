import { Employee } from "./employee"

export interface Agency{ /*class with !*/
  "id": number
  "location": string
  "employees": Employee[]
}