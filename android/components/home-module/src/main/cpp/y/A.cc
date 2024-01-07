#include <GLES3/gl31.h>
#include <EGL/egl.h>
#include <EGL/eglext.h>
#include <android/looper.h>
#include <errno.h>
#include <fcntl.h>
#include <jni.h>
#include <sys/eventfd.h>
#include <sys/timerfd.h>
#include <sys/types.h>
#include <unistd.h>
#include <utility>
class MessagePumpForUI {
// The file descriptor used to signal that non-delayed work is available.
  int non_delayed_fd_;
// The file descriptor used to signal that delayed work is available.
  int delayed_fd_;
// The Android Looper for this thread.
//  raw_ptr<ALooper> looper_ = nullptr;
  ALooper *looper_ = nullptr;
  MessagePumpForUI();
  ~MessagePumpForUI();

};
int NonDelayedLooperCallback(int fd, int events, void *data) {
    return 1;
}
int DelayedLooperCallback(int fd, int events, void *data) {
    return 1;
}
MessagePumpForUI::MessagePumpForUI() {
  non_delayed_fd_ = eventfd(0, EFD_NONBLOCK | EFD_CLOEXEC);
//  CHECK_NE(non_delayed_fd_, -1);
//  DCHECK_EQ(TimeTicks::GetClock(), TimeTicks::Clock::LINUX_CLOCK_MONOTONIC);
  delayed_fd_ = reinterpret_cast<int>(
	  timerfd_create(CLOCK_MONOTONIC, TFD_NONBLOCK | TFD_CLOEXEC));
//  CHECK_NE(delayed_fd_, -1);
//  DCHECK(looper_);
  // Add a reference to the looper so it isn't deleted on us.
//  ALooper_acquire(looper_);
//  ALooper_addFd(looper_, non_delayed_fd_, 0, ALOOPER_EVENT_INPUT,
//				&NonDelayedLooperCallback, reinterpret_cast<void *>(this));
//  ALooper_addFd(looper_, delayed_fd_, 0, ALOOPER_EVENT_INPUT,
//				&DelayedLooperCallback, reinterpret_cast<void *>(this));
}
MessagePumpForUI::~MessagePumpForUI() {
//  DCHECK_EQ(ALooper_forThread(), looper_);
//  ALooper_removeFd(looper_, non_delayed_fd_);
//  ALooper_removeFd(looper_, delayed_fd_);
//  ALooper_release(looper_);
//  looper_ = nullptr;
//  close(non_delayed_fd_);
//  close(delayed_fd_);
}

void a() {

}