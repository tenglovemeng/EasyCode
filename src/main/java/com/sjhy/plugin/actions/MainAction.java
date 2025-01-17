package com.sjhy.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.sjhy.plugin.entity.ColumnSetting;
import com.sjhy.plugin.entity.TableInfo;
import com.sjhy.plugin.service.TableInfoService;
import com.sjhy.plugin.tool.CacheDataUtils;
import com.sjhy.plugin.ui.SelectSavePath;
import com.sjhy.plugin.ui.TableSettingDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * 代码生成菜单
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class MainAction extends AnAction {
    /**
     * 构造方法
     *
     * @param text 菜单名称
     */
    MainAction(@Nullable String text) {
        super(text);
    }

    /**
     * 处理方法
     *
     * @param event 事件对象
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }

        // 校验类型映射
        if (!TableInfoService.getInstance(project).typeValidator(CacheDataUtils.getInstance().getSelectDbTable())) {
            // 没通过不打开窗口
            return;
        }
//        //开始处理
//        new SelectSavePath(event.getProject()).open();
        TableSettingDialog tableSettingDialog = new TableSettingDialog(CacheDataUtils.getInstance().getSelectDbTable());
        tableSettingDialog.open();
        Map<String, ColumnSetting> tableSetting = tableSettingDialog.getTableSetting();
        TableInfo tableInfo = new TableInfo(CacheDataUtils.getInstance().getSelectDbTable(), tableSetting);
        new SelectSavePath(project,tableInfo).open();
    }
}
