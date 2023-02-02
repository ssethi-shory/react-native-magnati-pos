import React, { useCallback } from 'react';
import { useEffect, useState } from 'react';
import { Platform, TextInput } from 'react-native';

import {
  StyleSheet,
  View,
  Text,
  Button,
  PermissionsAndroid,
  Alert,
} from 'react-native';
import {
  initializePOS,
  startTransactionMode,
  stopTransactionMode,
  initializePayment,
  getTID_MID,
} from 'react-native-magnati-pos';

const sleep = (timeInSec: number) => {
  return new Promise((r) => setTimeout(r, timeInSec * 1000));
};
const App = () => {
  const [isLoading, setLoading] = useState(false);
  const [amount, setAmount] = useState('');

  const initialize = useCallback(async () => {
    setLoading(true);
    requestBluetoothPermission();
    requestWritePermission();
    const initRes = await initializePOS({
      transactionTimeout: 90,
      uuid: '00001101-0000-1000-8000-00805F9B34FB',
      connectionTimeout: 30,
      settlementTimeout: 300,
      enableTrace: true,
    });
    console.log(initRes);
    setLoading(false);
  }, []);

  useEffect(() => {
    initialize();
  }, [initialize]);

  const requestBluetoothPermission = async () => {
    try {
      if (Platform.OS === 'android') {
        const granted = await PermissionsAndroid.request(
          //@ts-ignore
          PermissionsAndroid.PERMISSIONS.BLUETOOTH_CONNECT,
          {
            title: 'Connect to nearby bluetooth devices',
            message: 'POS machine are detected by bluetooth',
            buttonNeutral: 'Ask Me Later',
            buttonNegative: 'Cancel',
            buttonPositive: 'OK',
          }
        );
        if (granted === PermissionsAndroid.RESULTS.GRANTED) {
          console.log('Bluetooth enabled');
        } else {
          console.log('Bluetooth permission denied');
        }
      }
    } catch (err) {
      console.warn(err);
    }
  };

  const requestWritePermission = async () => {
    try {
      if (Platform.OS === 'android') {
        const granted = await PermissionsAndroid.request(
          //@ts-ignore
          'android.permission.WRITE_EXTERNAL_STORAGE',
          {
            title: 'Connect to External storage ',
            message: '',
            buttonNeutral: 'Ask Me Later',
            buttonNegative: 'Cancel',
            buttonPositive: 'OK',
          }
        );
        if (granted === PermissionsAndroid.RESULTS.GRANTED) {
          console.log('External storage enabled');
        } else {
          console.log('External storage permission denied');
        }
      }
    } catch (err) {
      console.warn(err);
    }
  };
  const getTidMid = async () => {
    const startRes = await startTransactionMode();
    console.log('starting res', startRes);
    if (startRes.success) {
      await sleep(4);
      console.log('getting tid');
      const getTidResponse = await getTID_MID(10);
      console.log(getTidResponse);
    }
    const stopRes = await stopTransactionMode();
    console.log(stopRes);
  };

  const allSteps = async () => {
    let transactionAmount = Number(amount);
    if (transactionAmount > 1) {
      setLoading(true);
      const startRes = await startTransactionMode();
      console.log('starting res', startRes);
      if (startRes.success) {
        await sleep(4);
        console.log('triggering payment');
        const initializePaymentResponse = await initializePayment(
          transactionAmount * 100,
          '00283933',
          '1234',
          60
        );
        console.log('payment res', initializePaymentResponse);
        if (!initializePaymentResponse.success)
          Alert.alert(
            `Error - ${initializePaymentResponse?.code} - ${initializePaymentResponse?.message}`
          );
        else
          Alert.alert(
            `Success - ${initializePaymentResponse?.code} - ${initializePaymentResponse?.message}`
          );
        console.log(initializePaymentResponse?.data);
        setLoading(false);
        await sleep(1);
        const stopRes = await stopTransactionMode();
        console.log(stopRes);
      } else {
        setLoading(false);
      }
    } else {
      Alert.alert(`Error - Amount should be greater than 1`);
    }
  };

  const stop = () => {
    stopTransactionMode()
      .then((stopRes) => console.log(stopRes))
      .catch((ex) => console.log(ex));
  };
  return (
    <View style={styles.container}>
      {isLoading && <Text>Loading... </Text>}
      <TextInput
        value={amount}
        style={styles.input}
        onChangeText={setAmount}
        placeholder="useless placeholder"
        keyboardType="numeric"
      />
      <Button disabled={isLoading} title="All Steps" onPress={allSteps} />
      <Button title="GET TID MID" onPress={getTidMid} />
      <Button title="Stop" onPress={stop} />
      <Button title="Reinitialize" onPress={initialize} />
      <Button
        title="Alert"
        onPress={() => {
          Alert.alert('hey');
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
});

export default App;
