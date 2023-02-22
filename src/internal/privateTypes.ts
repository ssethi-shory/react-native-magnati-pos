import type {
  IInitializeRequest,
  IMagnatiAuthResponse,
  IMagnatiResponse,
} from './types';

export interface ExposedNativeMethods {
  initializePOS: (_options: IInitializeRequest) => Promise<IMagnatiResponse>;
  startTransactionMode: () => Promise<IMagnatiResponse>;
  stopTransactionMode: () => Promise<IMagnatiResponse>;
  getTID_MID: (timeoutInSec: number) => Promise<IMagnatiResponse>;
  settleBatch: (tid: string) => Promise<IMagnatiResponse>;
  initializePayment: (
    amount: number,
    tid: string,
    receiptNumber: string,
    timeoutInSec: number
  ) => Promise<IMagnatiAuthResponse>;
}
