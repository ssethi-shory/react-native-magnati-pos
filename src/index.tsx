import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-magnati-pos' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const MagnatiPosIntegrationLibrary = NativeModules.MagnatiPosIntegrationLibrary
  ? NativeModules.MagnatiPosIntegrationLibrary
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export const initializePOS = (
  _options: IInitializeRequest
): Promise<any | unknown> => {
  const {
    transactionTimeout = 30,
    uuid,
    connectionTimeout = 120,
    settlementTimeout = 300,
    enableTrace,
  } = _options;

  const promise = new Promise((resolve, reject) => {
    MagnatiPosIntegrationLibrary.triggerPosInitialize(
      uuid,
      connectionTimeout,
      transactionTimeout,
      settlementTimeout,
      enableTrace,
      (errorRes: any) => reject(errorRes),
      (res: unknown) => resolve(res)
    );
  });
  return promise;
};

export const startTransactionMode = (): Promise<any | unknown> => {
  const promise = new Promise((resolve, reject) => {
    MagnatiPosIntegrationLibrary.startTransactionMode(
      (errorRes: any) => reject(errorRes),
      (res: unknown) => resolve(res)
    );
  });
  return promise;
};

export const stopTransactionMode = (): Promise<any | unknown> => {
  const promise = new Promise((resolve, reject) => {
    MagnatiPosIntegrationLibrary.stopTransactionMode(
      (errorRes: any) => reject(errorRes),
      (res: unknown) => resolve(res)
    );
  });
  return promise;
};

export const initializePayment = async (
  amount: number,
  tid: string,
  receiptNumber: string,
  timeoutInSec: number = 30
): Promise<any | unknown> => {
  const promise = new Promise((resolve, reject) => {
    MagnatiPosIntegrationLibrary.getAuthorization(
      amount,
      tid,
      receiptNumber,
      timeoutInSec,
      (errorRes: any) => {
        console.log('in errorRes');
        reject(errorRes);
      },
      (res: any) => {
        if (res.success) resolve(res);
        else reject(res);
      }
    );
  });
  return promise;
};

export const getTID_MID = async (
  timeoutInSec: number = 30
): Promise<any | unknown> => {
  const promise = new Promise((resolve, reject) => {
    MagnatiPosIntegrationLibrary.getTIDMID(
      timeoutInSec,
      (errorRes: any) => {
        console.log('in errorRes');
        reject(errorRes);
      },
      (res: any) => {
        if (res.success) resolve(res);
        else reject(res);
      }
    );
  });
  return promise;
};
