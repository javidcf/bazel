package(default_visibility = ["//visibility:public"])

alias(
    name = "sdk",
    actual = "//external:android/sdk",
)

android_library(
    name = "incremental_stub_application",
    deps = ["//src/tools/android/java/com/google/devtools/build/android/incrementaldeployment:incremental_stub_application"],
)

android_library(
    name = "incremental_split_stub_application",
    deps = ["//src/tools/android/java/com/google/devtools/build/android/incrementaldeployment:incremental_split_stub_application"],
)

alias(
    name = "aar_generator",
    actual = "//src/tools/android/java/com/google/devtools/build/android:AarGeneratorAction",
)

alias(
    name = "manifest_merger",
    actual = "//src/tools/android/java/com/google/devtools/build/android:ManifestMergerAction",
)

alias(
    name = "rclass_generator",
    actual = "//src/tools/android/java/com/google/devtools/build/android:RClassGeneratorAction",
)

alias(
    name = "resources_processor",
    actual = "//src/tools/android/java/com/google/devtools/build/android:AndroidResourceProcessingAction",
)

alias(
    name = "resource_merger",
    actual = "//src/tools/android/java/com/google/devtools/build/android:AndroidResourceMergingAction",
)

alias(
    name = "resource_parser",
    actual = "//src/tools/android/java/com/google/devtools/build/android:AndroidResourceParsingAction",
)

alias(
    name = "resource_shrinker",
    actual = "//src/tools/android/java/com/google/devtools/build/android:ResourceShrinkerAction",
)

alias(
    name = "resource_validator",
    actual = "//src/tools/android/java/com/google/devtools/build/android:AndroidResourceValidatorAction",
)

alias(
    name = "merge_dexzips",
    actual = "//src/tools/android/java/com/google/devtools/build/android/ziputils:reducer",
)

alias(
    name = "shuffle_jars",
    actual = "//src/tools/android/java/com/google/devtools/build/android/ziputils:mapper",
)

sh_binary(
    name = "dexbuilder",
    srcs = ["fail.sh"],
)

sh_binary(
    name = "dexmerger",
    srcs = ["fail.sh"],
)

sh_binary(
    name = "desugar_java8",
    srcs = ["fail.sh"],
)

# TODO(bazel-team): Figure out how to compute this for Bazel
filegroup(
    name = "desugar_java8_extra_bootclasspath",
    srcs = [],
    visibility = ["//visibility:public"],
)

alias(
    name = "IdlClass",
    actual = "//src/tools/android/java/com/google/devtools/build/android/idlclass:IdlClass",
)

filegroup(
    name = "jar_filter",
    srcs = ["//src/tools/android/java/com/google/devtools/build/android/ideinfo:JarFilter_deploy.jar"],
)

java_binary(
    name = "JarFilter",
    main_class = "com.google.devtools.build.android.ideinfo.JarFilter",
    visibility = ["//visibility:public"],
    runtime_deps = [":jar_filter_import"],
)

java_import(
    name = "jar_filter_import",
    jars = [":jar_filter"],
)

filegroup(
    name = "package_parser",
    srcs = ["//src/tools/android/java/com/google/devtools/build/android/ideinfo:PackageParser_deploy.jar"],
)

java_binary(
    name = "PackageParser",
    main_class = "com.google.devtools.build.android.ideinfo.PackageParser",
    visibility = ["//visibility:public"],
    runtime_deps = [":package_parser_import"],
)

java_import(
    name = "package_parser_import",
    jars = [":package_parser"],
)

py_binary(
    name = "merge_manifests",
    srcs = [
        "android_permissions.py",
        "merge_manifests.py",
    ],
    deps = [
        "//third_party/py/gflags",
    ],
)

py_test(
    name = "merge_manifests_test",
    srcs = ["merge_manifests_test.py"],
    deps = [":merge_manifests"],
)

py_binary(
    name = "build_incremental_dexmanifest",
    srcs = [":build_incremental_dexmanifest.py"],
    deps = [],
)

py_binary(
    name = "build_split_manifest",
    srcs = ["build_split_manifest.py"],
    deps = [
        "//third_party/py/gflags",
    ],
)

py_test(
    name = "build_split_manifest_test",
    srcs = ["build_split_manifest_test.py"],
    deps = [
        ":build_split_manifest",
    ],
)

py_binary(
    name = "incremental_install",
    srcs = ["incremental_install.py"],
    deps = [
        "//third_party/py/concurrent:futures",
        "//third_party/py/gflags",
    ],
)

py_test(
    name = "incremental_install_test",
    srcs = ["incremental_install_test.py"],
    deps = [
        ":incremental_install",
        "//third_party/py/mock",
    ],
)

py_binary(
    name = "strip_resources",
    srcs = ["strip_resources.py"],
    deps = [
        "//third_party/py/gflags",
    ],
)

py_binary(
    name = "aar_native_libs_zip_creator",
    srcs = [
        "aar_native_libs_zip_creator.py",
    ],
    deps = [
        "//third_party/py/gflags",
    ],
)

py_binary(
    name = "stubify_manifest",
    srcs = ["stubify_manifest.py"],
    deps = [
        "//third_party/py/gflags",
    ],
)

py_test(
    name = "stubify_manifest_test",
    srcs = ["stubify_manifest_test.py"],
    deps = [
        ":stubify_manifest",
    ],
)

py_test(
    name = "aar_native_libs_zip_creator_test",
    srcs = [
        "aar_native_libs_zip_creator_test.py",
    ],
    deps = [
        ":aar_native_libs_zip_creator",
    ],
)

sh_binary(
    name = "rex_wrapper",
    srcs = ["fail.sh"],
)

sh_binary(
    name = "zip_manifest_creator",
    srcs = ["zip_manifest_creator.sh"],
    data = ["//tools/zip:zipper"],
)

sh_test(
    name = "zip_manifest_creator_test",
    size = "small",
    srcs = ["zip_manifest_creator_test.sh"],
    data = [":zip_manifest_creator"],
)

py_binary(
    name = "aar_embedded_jars_extractor",
    srcs = ["aar_embedded_jars_extractor.py"],
    deps = ["//third_party/py/gflags"],
)

py_test(
    name = "aar_embedded_jars_extractor_test",
    srcs = ["aar_embedded_jars_extractor_test.py"],
    deps = [":aar_embedded_jars_extractor"],
)

py_binary(
    name = "resource_extractor",
    srcs = ["resource_extractor.py"],
)

py_test(
    name = "resource_extractor_test",
    srcs = ["resource_extractor_test.py"],
    deps = [":resource_extractor"],
)

# If needed, this file can be regenerated by (validity is 30 * 365 days):
# keytool -genkeypair \
#    -alias androiddebugkey \
#    -dname "CN=Android Debug, O=Android, C=US" \
#    -keystore cica \
#    -sigalg SHA256withDSA \
#    -validity 10950
filegroup(
    name = "debug_keystore",
    srcs = ["bazel_debug.keystore"],
)

filegroup(
    name = "srcs",
    srcs = glob(["**"]),
)
