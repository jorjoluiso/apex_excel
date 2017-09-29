--------------------------------------------------------
--  DDL for View V_SUPER_TOTALES
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "V_SUPER_TOTALES" ("ORDEN", "DESCRIPCION", "EXPRESADO", "MORA_HASTA_30", "MORA_31_60", "MORA_MAS_61", "NO_VENCIDO", "VALOR", "ABONO", "SALDO") AS 
  with data as(    
SELECT
    1 as orden,
    'Totales de Documentos a Crédito:' as descripcion,
    '$' as expresado,
    SUM(mora_hasta_30) as mora_hasta_30,
    SUM(mora_31_60) as mora_31_60,
    SUM(mora_mas_61) as mora_mas_61,
    SUM(no_vencido) as no_vencido,
    nvl(SUM(valor),0) as valor,
    SUM(abono) as abono,
    SUM(saldo) as saldo
FROM
    v_super_morosidades
union
    
SELECT
    2,
    'Conteo Clientes Morosidad:',
        'Unidad' as expresado,
    PKG_SUPER.FUN_COUNT_MORA_HASTA_30,
    PKG_SUPER.FUN_COUNT_MORA_31_60,
    PKG_SUPER.FUN_COUNT_MORA_MAS_61,
    null,
    null,
    null,
    null
FROM
    dual
UNION
SELECT
    4,
    'Totales Ventas Contado: ',
        '$' as expresado,
    null,
    null,
    null,
    null,
    pkg_super.fun_totales_ventas_contado('FAC'),
    null,
    null
FROM dual
UNION
SELECT
    5,
    'Totales Notas de Ventas Contado: ',
        '$' as expresado,
    null,
    null,
    null,
    null,
    pkg_super.fun_totales_ventas_contado('NVE'),
    null,
    null
FROM dual
UNION
SELECT
    6,
    'Totales Notas de Crédito por Devoluciones: ',
        '$' as expresado,
    null,
    null,
    null,
    null,
    pkg_super.fun_totales_devoluciones,
    null,
    null
FROM dual
UNION
SELECT
    7,
    'Totales Notas de Crédito por Descuento: ',
        '$' as expresado,
    null,
    null,
    null,
    null,
    pkg_super.fun_totales_notas_credito,
    null,
    null
FROM dual
UNION
SELECT
    8,
    'Conteo Total Clientes Crédito: ',
        'Unidad' as expresado,

    null,
    null,
    null,
    null,
    pkg_super.fun_count_cliente_credito,
    null,
    null
FROM dual


)
select  orden,
  descripcion,
expresado,
   mora_hasta_30,
  mora_31_60,
  mora_mas_61,
  no_vencido,
  valor,
  abono,
  saldo
  from data
order by orden asc
;
