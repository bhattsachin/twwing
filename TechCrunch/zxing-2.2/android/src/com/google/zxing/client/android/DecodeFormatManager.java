/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Intent;
import android.net.Uri;
import com.google.zxing.BarcodeFormatNow;

final class DecodeFormatManager {

  private static final Pattern COMMA_PATTERN = Pattern.compile(",");

  static final Collection<BarcodeFormatNow> PRODUCT_FORMATS;
  static final Collection<BarcodeFormatNow> ONE_D_FORMATS;
  static final Collection<BarcodeFormatNow> QR_CODE_FORMATS = EnumSet.of(BarcodeFormatNow.QR_CODE);
  static final Collection<BarcodeFormatNow> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormatNow.DATA_MATRIX);
  static {
    PRODUCT_FORMATS = EnumSet.of(BarcodeFormatNow.UPC_A,
                                 BarcodeFormatNow.UPC_E,
                                 BarcodeFormatNow.EAN_13,
                                 BarcodeFormatNow.EAN_8,
                                 BarcodeFormatNow.RSS_14,
                                 BarcodeFormatNow.RSS_EXPANDED);
    ONE_D_FORMATS = EnumSet.of(BarcodeFormatNow.CODE_39,
                               BarcodeFormatNow.CODE_93,
                               BarcodeFormatNow.CODE_128,
                               BarcodeFormatNow.ITF,
                               BarcodeFormatNow.CODABAR);
    ONE_D_FORMATS.addAll(PRODUCT_FORMATS);
  }

  private DecodeFormatManager() {}

  static Collection<BarcodeFormatNow> parseDecodeFormats(Intent intent) {
    List<String> scanFormats = null;
    String scanFormatsString = intent.getStringExtra(Intents.Scan.FORMATS);
    if (scanFormatsString != null) {
      scanFormats = Arrays.asList(COMMA_PATTERN.split(scanFormatsString));
    }
    return parseDecodeFormats(scanFormats, intent.getStringExtra(Intents.Scan.MODE));
  }

  static Collection<BarcodeFormatNow> parseDecodeFormats(Uri inputUri) {
    List<String> formats = inputUri.getQueryParameters(Intents.Scan.FORMATS);
    if (formats != null && formats.size() == 1 && formats.get(0) != null){
      formats = Arrays.asList(COMMA_PATTERN.split(formats.get(0)));
    }
    return parseDecodeFormats(formats, inputUri.getQueryParameter(Intents.Scan.MODE));
  }

  private static Collection<BarcodeFormatNow> parseDecodeFormats(Iterable<String> scanFormats,
                                                              String decodeMode) {
    if (scanFormats != null) {
      Collection<BarcodeFormatNow> formats = EnumSet.noneOf(BarcodeFormatNow.class);
      try {
        for (String format : scanFormats) {
          formats.add(BarcodeFormatNow.valueOf(format));
        }
        return formats;
      } catch (IllegalArgumentException iae) {
        // ignore it then
      }
    }
    if (decodeMode != null) {
      if (Intents.Scan.PRODUCT_MODE.equals(decodeMode)) {
        return PRODUCT_FORMATS;
      }
      if (Intents.Scan.QR_CODE_MODE.equals(decodeMode)) {
        return QR_CODE_FORMATS;
      }
      if (Intents.Scan.DATA_MATRIX_MODE.equals(decodeMode)) {
        return DATA_MATRIX_FORMATS;
      }
      if (Intents.Scan.ONE_D_MODE.equals(decodeMode)) {
        return ONE_D_FORMATS;
      }
    }
    return null;
  }

}
