# https://github.com/vercel/ms/blob/master/src/index.ts
import re

s = 1000
m = s * 60
h = m * 60
d = h * 24
w = d * 7
y = d * 365.25

def ms_parse(text: str) -> int:
    if len(text) > 100:
        raise ValueError("Value exceeds the maximum length of 100 characters.")
    
    # /^(?<value>-?(?:\d+)?\.?\d+) *(?<type>milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|weeks?|w|years?|yrs?|y)?$/i
    regex = re.compile(r"^(?P<value>-?(?:\d+)?\.?\d+) *(?P<type>milliseconds?|msecs?|ms|seconds?|secs?|s|minutes?|mins?|m|hours?|hrs?|h|days?|d|weeks?|w|years?|yrs?|y)?$", re.IGNORECASE)
    match = regex.match(text)
    if match is None:
        raise ValueError("Invalid time value.")
    values = match.groupdict()
    n = float(values["value"])
    typ = values["type"]

    if typ is None:
        return round(n)
    
    typ = typ.lower()
    if 'years'.startswith(typ):
        return round(n * y)
    if 'weeks'.startswith(typ):
        return round(n * w)
    if 'days'.startswith(typ):
        return round(n * d)
    if 'hours'.startswith(typ):
        return round(n * h)
    if 'minutes'.startswith(typ):
        return round(n * m)
    if 'seconds'.startswith(typ):
        return round(n * s)
    if 'milliseconds'.startswith(typ):
        return round(n)
    raise ValueError("Invalid time unit.")
