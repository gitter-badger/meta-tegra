DESCRIPTION = "NVIDIA prebuilt binary-only GStreamer plugins"
L4T_DEB_COPYRIGHT_MD5 = "e9ada95d2bb512af0bf751941ce6e6ba"

DEPENDS = "\
	glib-2.0 \
	gstreamer1.0-plugins-base \
	tegra-libraries-multimedia tegra-libraries-multimedia-utils \
	${@bb.utils.contains('DISTRO_FEATURES', ['x11', 'alsa'], 'virtual/libx11 alsa-lib', '', d)} \
	libdrm virtual/egl virtual/libgles2 \
"

L4T_DEB_TRANSLATED_BPN = "nvidia-l4t-gstreamer"

require tegra-debian-libraries-common.inc

MAINSUM = "16b2b02bcd3f0a4dce9c0a85976c5e0345e7e5af17a8ecab4fdf40797a5f74a0"

TEGRA_LIBRARIES_TO_INSTALL = "\
    libgstnvegl-1.0.so.0 \
    libgstnvexifmeta.so \
    libgstnvivameta.so \
    libnvsample_cudaprocess.so \
"
do_install() {
    install_libraries
    install -d ${D}${libdir}/gstreamer-1.0
    for f in ${S}/usr/lib/aarch64-linux-gnu/gstreamer-1.0/lib*; do
        install -m 0644 $f ${D}${libdir}/gstreamer-1.0/
    done
    # Remove the plugins we build from source
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvarguscamerasrc.so*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnveglglessink.so*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvjpeg.so*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvv4l2camerasrc.so*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvvideo4linux2.so*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvvideosinks.so*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvtee*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvdrmvideo*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvvidconv*
    rm -f ${D}${libdir}/gstreamer-1.0/libgstnvcompositor*
}

FILES_SOLIBSDEV = ""
FILES:${PN} = "${libdir}"
DEBIAN_NOAUTONAME:${PN} = "1"

RRECOMMENDS:${PN} = "gstreamer1.0-plugins-nvarguscamerasrc gstreamer1.0-plugins-nvv4l2camerasrc"
