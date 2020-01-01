import { NativeModules } from 'react-native';

const { HuaweiHms } = NativeModules;

const DEFAULT_OPTIONS = {
    title: 'Select a DEMO HMS',
};

module.exports = {
    ...HuaweiHms
}

