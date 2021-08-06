package common

import "time"

func Timestamp() int64 {
	return time.Now().UnixNano() / 1_000_000
}
