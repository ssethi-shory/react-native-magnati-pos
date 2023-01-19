interface IInitializeRequestOptions {
  uuid: string;
  connectionTimeout: number;
  transactionTimeout: number;
  settlementTimeout: number;
  enableTrace?: boolean;
}
interface IInitializeResponse {
  code: string;
  message: string;
}
