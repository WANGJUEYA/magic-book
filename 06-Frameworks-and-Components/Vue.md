---
title: Vue
summary: Vue
tags:
  - Vue
categories:
  - 06-Frameworks-and-Components
date: 2022-10-15 15:07:11
---

## VUE手动挂载节点

业务场景: 不想更改框架中的源码, 手动用新节点替换旧节点 `$mount`; 针对旧节点是在给定条件下展示, 使用watch去挂载

```js
import i18n from '@/locales'

export const ZVueBusinessListPageMixin = {
  watch: {
    operatorList (newValue) {
      const importExcelUrl = `${this.dataConfig.sysVarietyView.servicePrefix || ''}/base/tool/public/importPlus/${this.currentViewId}?enableInsertOrUpdate=false`
      const MyImportExcelProgress = Vue.extend(ImportExcelProgress)
      let that = this
      if ((newValue || []).filter(item => item.buttonCode === 'disableImportOrUpdate').length) {
        that.$nextTick(() => {
          new MyImportExcelProgress({
            i18n: i18n,
            propsData: { importExcelUrl: importExcelUrl, buttonName: that.$t('导入不启用覆盖') }
          })
            .$mount('#ListButton-disableImportOrUpdate')
        })
      }
    }
  },
}
```