import Foundation

@objc public class Pelicano: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
