interface IInitializeRequest {
  uuid: string;
  connectionTimeout?: number;
  transactionTimeout?: number;
  settlementTimeout?: number;
  enableTrace?: boolean;
}

interface IMagnatiResponse {
  code: string;
  message: string;
  success: boolean;
}
interface IMagnatiAuthResponseData {
  VFI_PayByOrderNo: string;
  VFI_PYCOPTInOut: string;
  VFI_PYCCurrencyName: string;
  VFI_PYCCurrencyCode: string;
  VFI_PYCAmount: string;
  VFI_PYCExchangeRate: string;
  VFI_CashAmount: string;
  VFI_EMVAC: string;
  VFI_EMVCID: string;
  VFI_EMVTSI: string;
  VFI_TXNTYPE: string;
  VFI_EMVTVR: string;
  VFI_QRVoucherNo: string;
  VFI_ECRNO: string;
  VFI_Mid: string;
  VFI_AdditionalInfo: string;
  VFI_EMVAID: string;
  VFI_QVEPS: string;
  VFI_RRN: string;
  VFI_EMVLabel: string;
  VFI_AuthMode: string;
  VFI_PYCMarkup: string;
  VFI_ApprovalCode: string;
  VFI_TransSource: string;
  VFI_CHVerification: string;
  VFI_MESSNO: string;
  VFI_TXNAMT: string;
  VFI_Batch: string;
  VFI_CHName: string;
  VFI_Expiry: string;
  VFI_PayByMerchantOrderNo: string;
  VFI_TID: string;
}

interface IMagnatiAuthResponse extends IMagnatiResponse {
  data: IMagnatiAuthResponseData | {};
}

interface IInitializePaymentRequest {
  amount: number;
  tid: string;
  receiptNo: string;
}
