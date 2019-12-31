import { NativeModules } from 'react-native';

const { HuaweiHms } = NativeModules;

const DEFAULT_OPTIONS = {
    title: 'Select a DEMO HMS',
};

module.exports = {
    ...HuaweiHms,
    showHMSDemo: function showImagePicker(options, callback) {
        if (typeof options === 'function') {
          callback = options;
          options = {};
        }
        return HuaweiHms.showHMSDemo({...DEFAULT_OPTIONS, ...options}, callback)
    },
}

