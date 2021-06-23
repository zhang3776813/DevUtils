package dev.engine.storage;

import android.net.Uri;
import android.os.Build;

import java.io.File;

import dev.base.DevSource;
import dev.engine.storage.listener.OnInsertListener;
import dev.utils.app.MediaStoreUtils;
import dev.utils.app.SDCardUtils;
import dev.utils.app.UriUtils;
import dev.utils.common.FileUtils;
import dev.utils.common.StringUtils;

/**
 * detail: DevUtils MediaStore Engine 实现
 * @author Ttt
 */
public class DevMediaStoreEngineImpl
        implements IStorageEngine<StorageItem, StorageResult> {

    // ==========
    // = 外部存储 =
    // ==========

    /**
     * 插入一张图片到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertImageToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToExternal(params, source, listener, TYPE.IMAGE);
        }
    }

    /**
     * 插入一条视频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertVideoToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToExternal(params, source, listener, TYPE.VIDEO);
        }
    }

    /**
     * 插入一条音频到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertAudioToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToExternal(params, source, listener, TYPE.AUDIO);
        }
    }

    /**
     * 插入一条文件资源到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertDownloadToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToExternal(params, source, listener, TYPE.DOWNLOAD);
        }
    }

    /**
     * 插入一条多媒体资源到外部存储空间 ( SDCard )
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertMediaToExternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToExternal(params, source, listener, TYPE.NONE);
        }
    }

    // ==========
    // = 内部存储 =
    // ==========

    /**
     * 插入一张图片到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertImageToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToInternal(params, source, listener, TYPE.IMAGE);
        }
    }

    /**
     * 插入一条视频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertVideoToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToInternal(params, source, listener, TYPE.VIDEO);
        }
    }

    /**
     * 插入一条音频到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertAudioToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToInternal(params, source, listener, TYPE.AUDIO);
        }
    }

    /**
     * 插入一条文件资源到内部存储空间
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertDownloadToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToInternal(params, source, listener, TYPE.DOWNLOAD);
        }
    }

    /**
     * 插入一条多媒体资源到内部存储空间
     * <pre>
     *     并不局限于多媒体, 如文本存储、其他文件写入等
     * </pre>
     * @param params   {@link EngineItem}
     * @param source   {@link DevSource}
     * @param listener {@link OnInsertListener}
     */
    @Override
    public void insertMediaToInternal(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        if (insidePreCheck(params, source, listener)) {
            insideInsertToInternal(params, source, listener, TYPE.NONE);
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    // ========
    // = Type =
    // ========

    /**
     * detail: 存储操作类型
     * @author Ttt
     */
    private enum TYPE {
        IMAGE,
        VIDEO,
        AUDIO,
        DOWNLOAD,
        NONE
    }

    /**
     * 当属于 NONE 类型则进行只能校验
     * @param params 原始参数
     * @param source 原始数据
     * @param type   存储操作类型
     * @return 输出 Uri
     */
    private TYPE convertType(
            StorageItem params,
            DevSource source,
            final TYPE type
    ) {
        if (type == TYPE.NONE) {

        }
        return type;
    }

    /**
     * 通用内部预校验
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @return {@code true} 校验通过, {@code false} 校验失败
     */
    private boolean insidePreCheck(
            StorageItem params,
            DevSource source,
            OnInsertListener<StorageItem, StorageResult> listener
    ) {
        // 判断参数是否有效
        if (params != null && source != null && source.isSource()) {
            return true;
        }
        // 无效数据触发回调
        if (listener != null) {
            listener.onResult(StorageResult.failure(), params, source);
        }
        return false;
    }

    // ============
    // = Uri、File =
    // ============

    /**
     * 获取输出 Uri ( 存储文件 Uri )
     * @param params   原始参数
     * @param source   原始数据
     * @param external 是否外部存储
     * @param type     存储操作类型
     * @return 输出 Uri
     */
    private Uri getOutputUri(
            final StorageItem params,
            final DevSource source,
            final boolean external,
            final TYPE type
    ) {
        if (params != null && source != null && source.isSource()) {
            if (params.getOutputUri() != null) {
                return params.getOutputUri();
            }
            // 外部存储才需要进行适配
            if (external) {
                switch (type) {
                    case IMAGE: // 存储到 Pictures 文件夹
                        // 创建 Image Uri
                        return MediaStoreUtils.createImageUri(
                                params.getFileName(),
                                params.getMimeType(),
                                params.getFolder()
                        );
                    case VIDEO: // 存储到 DCIM 文件夹
                        // 创建 Video Uri
                        return MediaStoreUtils.createVideoUri(
                                params.getFileName(),
                                params.getMimeType(),
                                params.getFolder()
                        );
                    case AUDIO: // 存储到 Music 文件夹
                        // 创建 Audio Uri
                        return MediaStoreUtils.createAudioUri(
                                params.getFileName(),
                                params.getMimeType(),
                                params.getFolder()
                        );
                    case DOWNLOAD: // 存储到 Download 文件夹
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // 创建 Download Uri
                            return MediaStoreUtils.createDownloadUri(
                                    params.getFileName(),
                                    params.getMimeType(),
                                    params.getFolder()
                            );
                        } else {
                            // 低版本直接创建 SDCard/Download File
                            File file = getOutputFile(params, source, external, type);
                            return MediaStoreUtils.createUriByFile(file);
                        }
                }
            } else { // 内部存储路径
                File file = getOutputFile(params, source, external, type);
                // FileProvider File Path Uri
                return UriUtils.getUriForFile(file);
            }
        }
        return null;
    }

    /**
     * 获取输出文件路径
     * @param params   原始参数
     * @param source   原始数据
     * @param external 是否外部存储
     * @param type     存储操作类型
     * @return 输出文件路径
     */
    private File getOutputFile(
            final StorageItem params,
            final DevSource source,
            final boolean external,
            final TYPE type
    ) {
        if (params != null && source != null && source.isSource()) {
            if (params.getOutputUri() != null) {
                String uriPath = UriUtils.getFilePathByUri(
                        params.getOutputUri()
                );
                if (uriPath != null) {
                    return FileUtils.getFile(uriPath);
                }
            }
            if (external) { // 外部存储路径
                switch (type) {
                    case IMAGE: // 存储到 Pictures 文件夹
                    case VIDEO: // 存储到 DCIM 文件夹
                    case AUDIO: // 存储到 Music 文件夹
                    case DOWNLOAD: // 存储到 Download 文件夹
                        // 需考虑 fileName 是否存在后缀情况 ( 如果 mimeType 用了 xxx/* 则需指定后缀 )
                        if (StringUtils.isNotEmpty(params.getMimeType())) {
                            // 属于 * 自动识别, 则 fileName 要求指定后缀 ( 直接拼接即可 )
                            if (params.getMimeType().contains("*")) {
                                // SDCard/folder/fileName
                                return FileUtils.getFile(
                                        SDCardUtils.getSDCardPath(params.getFolder()),
                                        params.getFileName()
                                );
                            } else {
                                // 进行获取文件后缀 ( 不含 . )
                                String extension = MediaStoreUtils.getExtensionFromMimeType(
                                        params.getMimeType()
                                );
                                // 存在后缀才进行拼接
                                if (StringUtils.isNotEmpty(extension)) {
                                    // fileName.extension ( 小写后缀 )
                                    String fileName = params.getFileName() + "." + extension.toLowerCase();
                                    // SDCard/folder/fileName.extension
                                    return FileUtils.getFile(
                                            SDCardUtils.getSDCardPath(params.getFolder()),
                                            fileName
                                    );
                                }
                                // 无法获取到后缀, 可考虑也进行拼接返回
                                // SDCard/folder/fileName
                                return FileUtils.getFile(
                                        SDCardUtils.getSDCardPath(params.getFolder()),
                                        params.getFileName()
                                );
                            }
                        }
                        break;
                }
            } else { // 内部存储路径
                String internalPath = params.getFilePath();
                // 判断是否存在文件夹, 存在则追加到 存储路径 上
                if (StringUtils.isNotEmpty(params.getFolder())) {
                    File internalFile = FileUtils.getFile(
                            params.getFilePath(),
                            params.getFolder()
                    );
                    internalPath = FileUtils.getAbsolutePath(internalFile);
                }
                // filePath + folder + fileName
                return FileUtils.getFile(internalPath, params.getFileName());
            }
        }
        return null;
    }

    // =================
    // = 内部插入数据方法 =
    // =================

    /**
     * 内部插入数据方法 ( 外部存储空间 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param type     存储操作类型
     */
    private void insideInsertToExternal(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final TYPE type
    ) {
        insideInsert(
                params, source, listener, true,
                convertType(params, source, type)
        );
    }

    /**
     * 内部插入数据方法 ( 内部存储空间 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param type     存储操作类型
     */
    private void insideInsertToInternal(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final TYPE type
    ) {
        insideInsert(
                params, source, listener, false,
                convertType(params, source, type)
        );
    }

    /**
     * 内部插入数据方法 ( 通用 )
     * @param params   原始参数
     * @param source   原始数据
     * @param listener 回调接口
     * @param external 是否外部存储
     * @param type     存储操作类型
     */
    private void insideInsert(
            final StorageItem params,
            final DevSource source,
            final OnInsertListener<StorageItem, StorageResult> listener,
            final boolean external,
            final TYPE type
    ) {
        // 获取输出 Uri
        Uri outputUri = getOutputUri(params, source, external, type);
        // 获取输出文件路径
        File outputFile = getOutputFile(params, source, external, type);
        // 通过 Uri 解析的路径
        String pathByUri = UriUtils.getFilePathByUri(outputUri);
        // 获取输入 URI 或 InputStream

//        // 开始写入数据 ( 后台线程 )
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();
    }
}