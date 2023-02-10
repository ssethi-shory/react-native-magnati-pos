import { NativeModules, Platform } from 'react-native';
import type { IInitializeRequest, IMagnatiAuthResponse, IMagnatiResponse } from './typings';

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
): Promise<IMagnatiResponse> => {
  const {
    transactionTimeout = 30,
    uuid,
    connectionTimeout = 120,
    settlementTimeout = 300,
    enableTrace,
  } = _options;
  return MagnatiPosIntegrationLibrary.triggerPosInitialize(
    uuid,
    connectionTimeout,
    transactionTimeout,
    settlementTimeout,
    enableTrace
  );
};

export const startTransactionMode = (): Promise<IMagnatiResponse> =>
  MagnatiPosIntegrationLibrary.startTransactionMode();

export const stopTransactionMode = (): Promise<IMagnatiResponse> =>
  MagnatiPosIntegrationLibrary.stopTransactionMode();

export const initializePayment = async (
  amount: number,
  tid: string,
  receiptNumber: string,
  timeoutInSec: number = 30
): Promise<IMagnatiAuthResponse> =>
  MagnatiPosIntegrationLibrary.getAuthorization(
    amount,
    tid,
    receiptNumber,
    timeoutInSec
  );

export const getTID_MID = async (
  timeoutInSec: number = 30
): Promise<IMagnatiResponse> =>
  MagnatiPosIntegrationLibrary.getTIDMID(timeoutInSec);
