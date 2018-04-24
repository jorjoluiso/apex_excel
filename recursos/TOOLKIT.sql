CREATE OR REPLACE PACKAGE Toolkit As
  FUNCTION encrypt (p_text  In  VARCHAR2) RETURN RAW;
  FUNCTION decrypt (p_raw  In  RAW) RETURN VARCHAR2;
END Toolkit;

/


CREATE OR REPLACE PACKAGE BODY Toolkit As
  ---- the custom key for 
  g_key     RAW(32767)  := UTL_RAW.cast_to_raw('20130417');
  ---- the custom padding character
  g_pad_chr VARCHAR2(1) := '_';

  PROCEDURE padstring (p_text  In OUT  VARCHAR2);

  FUNCTION encrypt (p_text  In  VARCHAR2) RETURN RAW Is
    l_text       VARCHAR2(32767) := p_text;
    l_encrypted  RAW(32767);
  BEGIN
    padstring(l_text);
    DBMS_OBFUSCATION_TOOLKIT.desencrypt(input          => UTL_RAW.cast_to_raw(l_text),
                                        Key            => g_key,
                                        encrypted_data => l_encrypted);
    RETURN l_encrypted;
  END;

  FUNCTION decrypt (p_raw  In  RAW) RETURN VARCHAR2 Is
    l_decrypted  VARCHAR2(32767);
  BEGIN
    DBMS_OBFUSCATION_TOOLKIT.desdecrypt(input => p_raw,
                                        Key   => g_key,
                                        decrypted_data => l_decrypted);

    RETURN Rtrim(UTL_RAW.cast_to_varchar2(l_decrypted), g_pad_chr);
  END;

  PROCEDURE padstring (p_text  In OUT  VARCHAR2) Is
    l_units  NUMBER;
  BEGIN
    IF Length(p_text) Mod 8 > 0 Then
      l_units := Trunc(Length(p_text)/8) + 1;
      p_text  := Rpad(p_text, l_units * 8, g_pad_chr);
    END IF;
  END;

END Toolkit;

/
