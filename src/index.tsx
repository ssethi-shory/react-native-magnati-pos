import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-magnati-pos-integration-library' doesn't seem to be linked. Make sure: \n\n` +
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

export function initializePOS(
  _options: IInitializeRequestOptions
): Promise<number> {
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
}
